package com.android.demo.m2;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

public class M3BeforeContactsActivity extends Module3BaseActivity {
    private M3BeforeContactsAdapter mAdapter;
    private RamRunnable mRamRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new M3BeforeContactsAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRamRunnable = new RamRunnable();
    }

    @Override
    void loadContacts() {
        mAdapter.addItems(mContacts);
    }

    @Override
    void loadImage() {
        //problem 3.3 huge amount of memory will leak on configuration change.
        Drawable drawable = RoundedBitmapDrawableFactory
                .create(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.background));
        mIvBackground.setImageDrawable(drawable);
    }

    @Override
    void startRamUsageThread() {
        //problem 3.2 an anonymous class will leak the activity instance on configuration change
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {
                        sleep(100);
                        mHandler.post(mRamRunnable);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //problem 3.2 : a non static inner class will leak the activity instance on configuration change.
    private class RamRunnable implements Runnable {
        protected final StringBuilder mStrBuilder=new StringBuilder();
        protected final Runtime mRuntime=Runtime.getRuntime();
        protected float mFreeRam;
        @Override
        public void run() {
            mFreeRam = (float) mRuntime.freeMemory();
            mStrBuilder
                    .append("Used: ")
                    .append((mRuntime.totalMemory() - mFreeRam) / MB_DIVIDER)
                    .append("MB\n")
                    .append("free:")
                    .append(mFreeRam / MB_DIVIDER);
            mTvRamUsage.setText(mStrBuilder.toString());
            mStrBuilder.delete(0, mStrBuilder.length());
        }
    }


}
