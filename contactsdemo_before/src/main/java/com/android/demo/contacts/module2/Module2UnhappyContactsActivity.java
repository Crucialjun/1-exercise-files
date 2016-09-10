package com.android.demo.contacts.module2;

import android.os.Bundle;

import com.android.demo.contacts.R;
import com.android.demo.contacts.model.Contact;
import com.android.demo.contacts.utils.ContactsUtils;

import java.util.ArrayList;

public class Module2UnhappyContactsActivity extends Module2BaseActivity {
    private Module2UnhappyContactsAdapter mAdapter;
    //problem 2.4
    private ArrayList<Contact> mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new Module2UnhappyContactsAdapter(getLayoutInflater(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    void loadImage() {
        //problem 2.2
        mIvBackground.setImageResource(R.drawable.background);
    }

    @Override
    void loadContacts() {
        //mContacts = MockContacts.mockListContacts();
        mAdapter.clear();
        mContacts= ContactsUtils.poorFetchAllContacts(getApplicationContext());
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
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                String ramUsage = String.format("%.3f MB\nfree:%.3f MB", ((float) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000), ((float) Runtime.getRuntime().freeMemory()) / 1000000);
                                mTvRamUsage.setText(ramUsage);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



    private void generateAsciiSumForAllContacts() {
        for (int i = 0; i < mContacts.size(); i++) {
            final Contact contact = mContacts.get(i);
            contact.generateAsciiSum();

        }
    }


}
