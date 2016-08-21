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

package omz.android.lovenasa.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import omz.android.lovenasa.R;
import omz.android.lovenasa.api.RestClient;
import omz.android.lovenasa.api.models.Patent;
import omz.android.lovenasa.api.models.PatentList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatentListFragment extends BaseFragment implements Callback<PatentList> {

    private final static String TAG = PatentListFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_list_patents, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);
        RestClient.getInstance().getClient().fetchPatents("space",100).enqueue(this);

    }


    ///////////////////////////////////////////////////Adapter and view holder definition//////////////////////////////////
    private class PatentsGridAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Patent> mItems;

        public PatentsGridAdapter(List<Patent> items) {
            mItems = items;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.list_item_patent, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Todo implement Detail actvitiy with will contain the full Patent object data: Title, description, contect information, telephone number
                }
            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            Patent patent = mItems.get(position);
            holder.textViewTitle.setText(patent.getTitle());

        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCard;

        public TextView textViewTitle;


        public ViewHolder(View view) {
            super(view);
            mCard = (CardView) view.findViewById(R.id.listCard);
            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);

        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    @Override
    public void onResponse(Call<PatentList> call, Response<PatentList> response) {

        PatentsGridAdapter adapter = new PatentsGridAdapter(response.body().getPatents());
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager staggeredLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredLayoutManager);
    }

    @Override
    public void onFailure(Call<PatentList> call, Throwable t) {
        if (t instanceof UnknownHostException ||t instanceof TimeoutException) {
            showInternetConnectionErrorSnackbar();
        }    }


}
