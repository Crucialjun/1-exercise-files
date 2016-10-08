package com.android.demo.m2;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import omz.android.baselib.M2BaseActivity;

public class M2AfterActivity extends M2BaseActivity {
    private static final String TAG = M2AfterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void loadImage() {
        Glide.with(this)
                .load(R.drawable.background)
                .override(mIvBackground.getWidth(), mIvBackground.getHeight())
                .into(mIvBackground);
    }
    private static final int INT_MB_DIVIDER = 1000000;
    private static final String STR = "Used:0.00MB\nfree:0.00MB";
    private final StringBuilder mStrBuilder = new StringBuilder(STR);
    private String formated;
    private final Runtime mRuntime = Runtime.getRuntime();
    private float mFreeRam,mTotalRam,mUsedRamShow,mFreeRamShow;
    @Override
    protected void startRamUsage() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    for (int i = 0; i < 500; i++) {
                        mHandler.post(() -> {
                            ////////////////2.2 Prefer to use StringBuilder for dynamic strings and extract constants where possible//////
                            extractValues();
                            formated=Float.toString(mUsedRamShow).substring(0,4);
                            mStrBuilder.delete(5,9);
                            mStrBuilder.insert(5,formated);

                            formated=Float.toString(mFreeRamShow).substring(0,4);
                            mStrBuilder.delete(17,21);
                            mStrBuilder.insert(17,formated);

                            mTvRamUsage.setText(mStrBuilder.toString());
                            /////////////////////////////////////////////////////////////////////////////////////////////////
                        });
                        sleep(16);
                    }
                    Log.d(TAG, "Finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void extractValues() {
        mFreeRam = (float) mRuntime.freeMemory();
        mTotalRam = (float) mRuntime.totalMemory();
        mFreeRamShow = mFreeRam / INT_MB_DIVIDER;
        mUsedRamShow = (mTotalRam - mFreeRam) / INT_MB_DIVIDER;
    }

    final static int ITERATIONS = 4000;

    @Override
    protected void runLoop() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                long sum = 0;
                for (int i = 0; i < ITERATIONS; i++) {
                    sum += betterPerformanceLoop();
                    sleepDelay();
                }
                Log.d(TAG, "Avg execution time : " + (float) sum / ITERATIONS);
            }
        }.start();
    }
    long timeStart = System.currentTimeMillis();
    final static double[] NUMS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    final static int LENGTH = NUMS.length;
    final static ArrayList<Double> arrayList = new ArrayList<>(LENGTH);
    public long betterPerformanceLoop() {
        arrayList.clear();
        timeStart = System.currentTimeMillis();
        for (short j = 0; j < LENGTH; j++)
            arrayList.add(NUMS[j]);
        return System.currentTimeMillis() - timeStart;
    }


}
