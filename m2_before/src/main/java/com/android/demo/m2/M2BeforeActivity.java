package com.android.demo.m2;

import android.os.Bundle;
import android.util.Log;

import com.android.demo.m2.model.Model;

import java.util.ArrayList;
import java.util.Random;

public class M2BeforeActivity extends Module2BaseActivity {
    private static final String TAG = M2BeforeActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    void loadImage() {
        //problem 2.2
        mIvBackground.setImageResource(R.drawable.background);
    }


    @Override
    void startRamUsageThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {
                        sleep(10);
                        mHandler.post(() -> {
                            String ramUsage = String.format("%.3f MB\nfree:%.3f MB", ((float) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000), ((float) Runtime.getRuntime().freeMemory()) / 1000000);
                            mTvRamUsage.setText(ramUsage);
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    ArrayList<Model> data = generateDataSet();

    @Override
    void runLoop() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                long sum = 0;
                for (int i = 0; i < 500; i++) {
                    sum += poorPerformanceLoop();
                    sleepDelay();
                }
                Log.d(TAG,"Avg execution time : " + (float) sum/500);

            }

        }.start();
    }
    private void sleepDelay() {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public long poorPerformanceLoop() {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < data.size(); i++) {
            Model model = data.get(i);
            int age = model.getAge();
            String family = model.getFamily();
            String name=model.getName();
            boolean flag= model.isFlag();
            String full=name+family;

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


}
