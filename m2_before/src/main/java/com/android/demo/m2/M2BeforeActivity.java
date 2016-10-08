package com.android.demo.m2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import omz.android.baselib.M2BaseActivity;

public class M2BeforeActivity extends M2BaseActivity {
    private static final String TAG = M2BeforeActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    final static int ITERATIONS = 40000;
    @Override
    protected void runLoop() {
        new Thread() {
            @Override
            public void run() {
                //****************************problem 2.1*****************************************
                super.run();
                long sum = 0;
                for (int i = 0; i < ITERATIONS; i++) {
                    sum += poorPerformanceLoop();
                    //sleepDelay();
                }
                Log.d(TAG, "Avg execution time : " + (float) sum / ITERATIONS);
            }
        }.start();
    }

    public long poorPerformanceLoop() {
        long timeStart = System.currentTimeMillis();
        ArrayList<Double> doubleNumbers = new ArrayList<>();
        for (int j = 0; j < 20; j++) {
            String[] stringsNums = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                    "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
            Double dObj = new Double(stringsNums[j]);
            doubleNumbers.add(dObj);
        }
        return System.currentTimeMillis() - timeStart;
    }

    @Override
    protected void startRamUsage() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    for (int i = 0; i < 500; i++) {
                        //*****************************************problem 2.2*********************************************
                        mHandler.post(() -> {
                            String ramUsage = String.format("%.2f", ((float) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000))+"MB\nfree:"+String.format("%.2f MB",(float) Runtime.getRuntime().freeMemory() / 1000000);
                            mTvRamUsage.setText(ramUsage);
                        });
                        sleep(10);
                    }
                    Log.d(TAG,"Finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void loadImage() {
        //*****************************************problem 2.3******************************************
        mIvBackground.setImageResource(R.drawable.background);

    }





}
