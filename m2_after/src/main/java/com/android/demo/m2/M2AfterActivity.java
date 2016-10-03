package com.android.demo.m2;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import omz.android.baselib.M2BaseActivity;

public class M2AfterActivity extends M2BaseActivity {
    private static final String TAG = M2AfterActivity.class.getSimpleName();
    protected static final int INT_MB_DIVIDER =1000000;
    protected static final String STR_FLOAT_FORMAT ="%.3f";


    private final StringBuilder mStrBuilder = new StringBuilder();
    private final Runtime mRuntime = Runtime.getRuntime();
    private float mFreeRam;
    private float mTotalRam;


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
    protected void startRamUsageThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {
                        sleep(20);
                        mHandler.post(() -> {
                            ////////////////2.3 Prefer to use StringBuilder for dynamic strings and extract constants where possible//////
                            mFreeRam = (float) mRuntime.freeMemory();
                            mTotalRam = (float) mRuntime.totalMemory();
                            mStrBuilder
                                    .append("Used: ")
                                    .append(String.format(STR_FLOAT_FORMAT, (mTotalRam - mFreeRam) / INT_MB_DIVIDER))
                                    .append("MB\n")
                                    .append("free:")
                                    .append(String.format(STR_FLOAT_FORMAT, mFreeRam / INT_MB_DIVIDER));
                            mTvRamUsage.setText(mStrBuilder.toString());
                            mStrBuilder.delete(0, mStrBuilder.length());
                            /////////////////////////////////////////////////////////////////////////////////////////////////
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    final static int ITERATIONS =4000;
    @Override
    protected void runLoop() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                long sum = 0;
                for (int i = 0; i < ITERATIONS; i++) {
                    sum += betterPerformanceLoop();
                    sleepDelay();
                }
                Log.d(TAG,"Avg execution time : " + (float) sum/ITERATIONS);
            }
        }.start();
    }

    long timeStart = System.currentTimeMillis();
    final static double[] NUMS={1,2,3,4,5,6,7,8,9,10,
            11,12,13,14,15,16,17,18,19,20};
    final static int LENGTH=NUMS.length;
    final static ArrayList<Double> arrayList=new ArrayList<>(LENGTH);
    public long betterPerformanceLoop() {
        arrayList.clear();
        timeStart = System.currentTimeMillis();
           for (short j = 0; j < LENGTH; j++)
               arrayList.add(NUMS[j]);
        return System.currentTimeMillis() - timeStart;
    }


}
