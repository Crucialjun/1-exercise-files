package com.android.demo.m2;

import android.os.Bundle;
import android.util.Log;

import com.android.demo.m2.model.Model;
import com.android.demo.m2.model.OptModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

import omz.android.baselib.M2BaseActivity;

public class M2AfterActivity extends M2BaseActivity {
    private static final String TAG = M2AfterActivity.class.getSimpleName();
    protected static final int MB_DIVIDER=1000000;
    protected static final String FLOAT_FORMAT="%.3f";


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
                                    .append(String.format(FLOAT_FORMAT, (mTotalRam - mFreeRam) / MB_DIVIDER))
                                    .append("MB\n")
                                    .append("free:")
                                    .append(String.format(FLOAT_FORMAT, mFreeRam / MB_DIVIDER));
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

    ArrayList<Model> data = generateDataSet();
    ArrayList<OptModel> optData = generateOptDataSet();

    @Override
    protected void runLoop() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                long sum = 0;
                for (int i = 0; i < 500; i++) {
                    sum += goodPerformanceLoop();
                }
                Log.d(TAG,"Avg execution time : " + (float) sum/500);
            }
        }.start();



    }

    long timeStart = System.currentTimeMillis();
    int size = data.size();
    OptModel model;
    String family, name;
    boolean flag;
    int age;
    public long goodPerformanceLoop() {
        timeStart = System.currentTimeMillis();
        size = data.size();
        for (int i = 0; i < size; i++) {
            model = optData.get(i);
            age = model.age;
            family = model.family;
            name = model.name;
            flag = model.flag;

        }
        return System.currentTimeMillis() - timeStart;
    }


    public ArrayList<Model> generateDataSet() {
        Random random = new Random();
        ArrayList<Model> data = new ArrayList();
        for (int i = 0; i < 1000; i++)
            data.add(new Model(true, "Android", random.nextInt(), "Performance"));

        return data;

    }
    public ArrayList<OptModel> generateOptDataSet() {
        Random random = new Random();
        ArrayList<OptModel> data = new ArrayList();
        for (int i = 0; i < 1000; i++)
            data.add(new OptModel(true, "Android", random.nextInt(), "Performance"));

        return data;

    }



}
