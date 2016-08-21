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

package omz.android.lovenasa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import omz.android.lovenasa.api.models.Apod;
import omz.android.lovenasa.utils.PaletteUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_OBJECT = "apod_object";

    private TextView mTextViewCopyrights;
    private TextView mTextViewDate;
    private TextView mTextViewTitle;
    private TextView mTextViewDescription;
    private CardView mInfoCardView;
    private CardView mDescriptionCardView;
    private ImageView mBackdropPhoto;
    private CollapsingToolbarLayout mCollapsingToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Apod apod = intent.getParcelableExtra(EXTRA_OBJECT);
        bindViews();
        bindData(apod);


    }

    private void bindViews() {
        mTextViewCopyrights = (TextView) findViewById(R.id.textViewCopyrights);
        mTextViewDate = (TextView) findViewById(R.id.textViewDate);
        mTextViewTitle = (TextView) findViewById(R.id.textViewTitle);
        mTextViewDescription = (TextView) findViewById(R.id.textViewDescription);
        mInfoCardView = (CardView) findViewById(R.id.infoCard);
        mDescriptionCardView = (CardView) findViewById(R.id.descriptionCard);
        mBackdropPhoto = (ImageView) findViewById(R.id.backdrop);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        findViewById(R.id.fabShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add sharing of image and image information
            }
        });
    }

    private void loadBackdrop(String imageUrl) {
        Glide.with(this).load(imageUrl)
                .asBitmap().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.image_place_holder)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {

                                Palette.Swatch swatch = PaletteUtils.getSwatch(palette);
                                int rgb = swatch.getRgb();
                                mInfoCardView.setBackgroundColor(rgb);
                                mDescriptionCardView.setBackgroundColor(rgb);
                                mBackdropPhoto.setImageBitmap(resource);
                                findViewById(R.id.main_content).setBackgroundColor(palette.getLightMutedColor(0xFFFFFF));
                            }
                        });

                    }
                });
    }


    private void bindData(Apod apod) {
        loadBackdrop(apod.getUrl());

        mCollapsingToolbar.setTitle(apod.getTitle());
        mTextViewTitle.setText(apod.getTitle());
        mTextViewCopyrights.setText(apod.getCopyright());
        mTextViewDate.setText(apod.getDate());
        mTextViewDescription.setText(apod.getExplanation());
    }

}
