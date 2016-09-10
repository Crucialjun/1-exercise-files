package com.android.demo.contacts.module2;

import android.os.Bundle;
import android.util.SparseArray;

import com.android.demo.contacts.R;
import com.android.demo.contacts.mock.MockContacts;
import com.android.demo.contacts.model.Contact;
import com.android.demo.contacts.utils.CallUtils;
import com.android.demo.contacts.utils.ContactsUtils;
import com.bumptech.glide.Glide;

public class Module2HappyContactsActivity extends Module2BaseActivity {
    private Module2HappyContactsAdapter mAdapter;
    private SparseArray<Contact> mContacts;

    private final StringBuilder mStrBuilder = new StringBuilder();
    private final Runtime mRuntime = Runtime.getRuntime();
    private float mFreeRam;

    Runnable ramRunnable = new Runnable() {
        @Override
        public void run() {
            ////////////////2.3 Prefer to use StringBuilder for dynamic strings and extract constants where possible//////
            mFreeRam = (float) mRuntime.freeMemory();
            mStrBuilder
                    .append("Used: ")
                    .append((mRuntime.totalMemory() - mFreeRam) / MB_DIVIDER)
                    .append("MB\n")
                    .append("free:")
                    .append(mFreeRam / MB_DIVIDER);
            mTvRamUsage.setText(mStrBuilder.toString());
            mStrBuilder.delete(0, mStrBuilder.length());
            /////////////////////////////////////////////////////////////////////////////////////////////////
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new Module2HappyContactsAdapter(getLayoutInflater(), this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    void loadImage() {
        Glide.with(this)
                .load(R.drawable.background)
                .override(mIvBackground.getWidth(), mIvBackground.getHeight())
                .into(mIvBackground);
    }

    @Override
    void loadContacts() {
        mAdapter.clear();
        mContacts = MockContacts.mockSpareContacts();
        mContacts= ContactsUtils.betterSparseFetchAllContacts(getApplicationContext());
        generateAsciiSumForAllContacts();
        mAdapter.addItems(mContacts);
    }


    @Override
    void startRamUsageThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {
                        sleep(100);
                        mHandler.post(ramRunnable);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void generateAsciiSumForAllContacts() {
        //2.3   Create one reference instead of a new reference for each iteration of the loop
        Contact contact;
        for (int i = 0; i < mContacts.size(); i++) {
            contact = mContacts.get(i);
            contact.generateAsciiSum();

        }
    }

    @Override
    public void callNumber(String number) {
        CallUtils.callNumber(this, number);
    }

}
