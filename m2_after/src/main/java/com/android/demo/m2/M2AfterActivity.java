package com.android.demo.m2;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import omz.android.baselib.M2BaseActivity;

public class M2AfterActivity extends M2BaseActivity {
    private static final String TAG = M2AfterActivity.class.getSimpleName();
    private static final int INT_MB_DIVIDER = 1000000;
    private static final String STR_USED = "Used:";
    private static final String STR_MB = "MB\n";
    private static final String STR_FLOAT_FREE = "free:";

    private final StringBuilder mStrBuilder = new StringBuilder();
    private final Runtime mRuntime = Runtime.getRuntime();
    private float mFreeRam;
    private float mTotalRam;
    private float mUsedRamShow;
    private float mFreeRamShow;
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
                            mFreeRam = (float) mRuntime.freeMemory();
                            mTotalRam = (float) mRuntime.totalMemory();
                            mFreeRamShow =mFreeRam / INT_MB_DIVIDER;
                            mUsedRamShow =(mTotalRam - mFreeRam) / INT_MB_DIVIDER;
                            mStrBuilder
                                    .append(STR_USED)
                                    .append(Float.toString(mUsedRamShow),0,4)
                                    .append(STR_MB)
                                    .append(STR_FLOAT_FREE)
                                    .append(Float.toString(mFreeRamShow),0,4);
                            mTvRamUsage.setText(mStrBuilder.toString());
                            mStrBuilder.delete(0, mStrBuilder.length());
                            /////////////////////////////////////////////////////////////////////////////////////////////////
                        });
                        sleep(16);
                    }
                    Log.d(TAG,"Finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
