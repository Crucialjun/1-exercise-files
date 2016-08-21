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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import omz.android.lovenasa.R;
import omz.android.lovenasa.api.RestClient;
import omz.android.lovenasa.api.models.Apod;
import omz.android.lovenasa.utils.PaletteUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApodFragment extends BaseFragment  implements Callback<Apod> {

    private final static String TAG = ApodFragment.class.getSimpleName();
    private ImageView mImageViewPhoto;
    private TextView mTextViewCopyrights;
    private TextView mTextViewDate;
    private TextView mTextViewTitle;
    private TextView mTextViewDescription;


    private CardView mInfoCardView;
    private CardView mDescriptionCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_apod, container, false);
        mImageViewPhoto = (ImageView) view.findViewById(R.id.ivApod);
        mTextViewCopyrights = (TextView) view.findViewById(R.id.textViewCopyrights);
        mTextViewDate = (TextView) view.findViewById(R.id.textViewDate);
        mTextViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        mTextViewDescription=(TextView) view.findViewById(R.id.textViewDescription);
        mInfoCardView = (CardView) view.findViewById(R.id.infoCard);
        mDescriptionCardView = (CardView) view.findViewById(R.id.descriptionCard);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestClient.getInstance().getClient().fetchApod().enqueue(this);

    }


    private void bindData(Apod apod) {
        Glide.with(this).load(apod.getUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.image_place_holder)
                .into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatch = PaletteUtils.getSwatch(palette);
                        int rgb=swatch.getRgb();
                        mInfoCardView.setBackgroundColor(rgb);
                        mDescriptionCardView.setBackgroundColor(rgb);
                        mImageViewPhoto.setImageBitmap(resource);

                    }
                });

            }
        });
        mTextViewTitle.setText(apod.getTitle());
        mTextViewCopyrights.setText(apod.getCopyright());
        mTextViewDate.setText(apod.getDate());
        mTextViewDescription.setText(apod.getExplanation());
    }


    @Override
    public void onResponse(Call<Apod> call, Response<Apod> response) {
        Apod apod = response.body();
        bindData(apod);
    }


    @Override
    public void onFailure(Call<Apod> call, Throwable t) {
        Log.d(TAG, "onFailure");
        if (t instanceof UnknownHostException ||t instanceof TimeoutException) {
            showInternetConnectionErrorSnackbar();
        }
    }




}
