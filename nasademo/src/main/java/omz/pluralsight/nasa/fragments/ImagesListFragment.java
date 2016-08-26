/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package omz.pluralsight.nasa.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.List;

import omz.pluralsight.nasa.DetailActivity;
import omz.pluralsight.nasa.R;
import omz.pluralsight.nasa.api.models.Apod;

public class ImagesListFragment extends BaseFragment {

    private final static String TAG = ImagesListFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_list_images, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Apod> images = loadImagesFromAssets();
        ImagesListAdapter adapter = new ImagesListAdapter(images);
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        setRetainInstance(true);

    }



    ///////////////////////////////////////////////////Adapter and view holder definition//////////////////////////////////

    private class ImagesListAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Apod> mItems;

        public ImagesListAdapter(List<Apod> items) {
            mItems = items;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.list_item_image, parent, false);
            final ViewHolder vh = new ViewHolder(view);
            vh.cardContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View decor = getActivity().getWindow().getDecorView();
                    View statusBar = decor.findViewById(android.R.id.statusBarBackground);
                    View navBar = decor.findViewById(android.R.id.navigationBarBackground);

                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_OBJECT, mItems.get((int) view.getTag()));

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            Pair.create(view.findViewById(R.id.image), getString(R.string.transition_name_shared_photo)),
                            Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME),
                            Pair.create(navBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));

                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());


                }
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            Apod apod = mItems.get(position);
            holder.textViewTitle.setText(apod.getTitle());
            holder.textViewDate.setText(apod.getDate());
            holder.textViewCopyRights.setText(apod.getCopyright());
            holder.cardContainer.setTag(position);
            Glide.with(getActivity()).load(apod.getUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new BitmapImageViewTarget(holder.imageViewBackground) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);

                            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    holder.cardContainer.setBackgroundColor(palette.getDarkMutedColor(Color.WHITE));

                                }
                            });
                        }


                    });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout cardContainer;
        public ImageView imageViewBackground;
        public TextView textViewTitle;
        public TextView textViewDate;
        public TextView textViewCopyRights;

        public ViewHolder(View view) {
            super(view);
            cardContainer = (LinearLayout) view.findViewById(R.id.infoCardContainer);
            imageViewBackground = (ImageView) view.findViewById(R.id.image);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            textViewCopyRights = (TextView) view.findViewById(R.id.textViewCopyrights);

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////Some Helper Methods//////////////////////////////////////////

    private List<Apod> loadImagesFromAssets() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(getResources().getAssets().open("apod_prefill_data.txt"),
                    TypeFactory.defaultInstance().constructCollectionType(List.class,
                            Apod.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}

