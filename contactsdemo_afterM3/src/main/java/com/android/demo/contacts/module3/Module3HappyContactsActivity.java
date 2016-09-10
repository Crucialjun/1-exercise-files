package com.android.demo.contacts.module3;

import android.os.Bundle;
import android.widget.TextView;

import com.android.demo.contacts.R;
import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

public class Module3HappyContactsActivity extends Module3BaseActivity {
    private Module3HappyContactsAdapter mAdapter;
    private Thread mRamThread;
    private RamRunnable mRamRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new Module3HappyContactsAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRamRunnable=new RamRunnable(mTvRamUsage);
    }


    @Override
    void loadImage() {
        Glide.with(this)
                .load(R.drawable.background)
                .override(mIvBackground.getWidth(),mIvBackground.getHeight())
                .into(mIvBackground);
    }

    @Override
    void loadContacts() {
        mAdapter.addItems(mContacts);
    }


    @Override
    void startRamUsageThread() {
        mRamThread= new Thread() {
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
        };
        mRamThread.start();
    }




    private static class RamRunnable implements Runnable {
        private WeakReference<TextView> mWeakTv;
        protected final StringBuilder mStrBuilder=new StringBuilder();
        protected final Runtime mRuntime=Runtime.getRuntime();
        protected float mFreeRam;
        public RamRunnable(TextView tv) {
            this.mWeakTv = new WeakReference<>(tv);
        }

        @Override
        public void run() {
            final TextView tvRam= mWeakTv.get();
            if (tvRam!=null) {
                mFreeRam = (float) mRuntime.freeMemory();
                mStrBuilder
                        .append("Used: ")
                        .append((mRuntime.totalMemory() - mFreeRam) / MB_DIVIDER)
                        .append("MB\n")
                        .append("free:")
                        .append(mFreeRam / MB_DIVIDER);
                tvRam.setText(mStrBuilder.toString());
                mStrBuilder.delete(0, mStrBuilder.length());
            }
        }
    }


    @Override
    protected void onDestroy() {
        mIvBackground.setImageDrawable(null);
        mRamThread.interrupt();
        mRamThread=null;
        super.onDestroy();
    }

}
