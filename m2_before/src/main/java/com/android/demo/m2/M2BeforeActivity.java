package com.android.demo.m2;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import omz.android.baselib.M2BaseActivity;
import omz.android.baselib.model.Model;

public class M2BeforeActivity extends M2BaseActivity {
    private static final String TAG = M2BeforeActivity.class.getSimpleName();
    ArrayList<Model> data = generateDataSet();

    @Override
    protected void runLoop() {
        new Thread(){
            @Override
            public void run() {
                //****************************problem 2.1*****************************************
                super.run();
                long sum = 0;
                for (int i = 0; i < 500; i++) {
                    sum += poorPerformanceLoop();
                    //sleepDelay();
                }
                Log.d(TAG,"Avg execution time : " + (float) sum/500);

            }

        }.start();
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
    private void sleepDelay() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void loadImage() {
<<<<<<< HEAD
        //problem 2.2
=======
        //*****************************************problem 2.2******************************************
>>>>>>> 68757ff18677e6b8972b5229b4cd5efd8cb80d46
        mIvBackground.setImageResource(R.drawable.background);

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
                        //*****************************************problem 2.3*********************************************
                        mHandler.post(() -> {
                            String ramUsage = String.format("%.3f MB\nfree:%.3f MB",
                                    ((float) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000),
                                    ((float) Runtime.getRuntime().freeMemory()) / 1000000);
                            mTvRamUsage.setText(ramUsage);
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }







}
