package com.android.demo.contacts;

import android.os.Bundle;
import android.util.Log;

import com.android.demo.contacts.model.Contact;
import com.android.demo.contacts.model.Model;
import com.android.demo.contacts.utils.ContactsUtils;

import java.util.ArrayList;
import java.util.Random;

public class M2BeforeContactsActivity extends Module2BaseActivity {
    private static final String TAG = M2BeforeContactsActivity.class.getSimpleName();
    private M2BeforeContactsAdapter mAdapter;
    //problem 2.4
    private ArrayList<Contact> mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new M2BeforeContactsAdapter(getLayoutInflater(), this);
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

    ArrayList<Model> data = generateDataSet();

    @Override
    void runLoop() {
        long sum = 0;
        for (int i = 0; i < 10000; i++) {
            sum += poorPerformanceLoop();
        }
        Log.d(TAG,"Avg execution time : " + (float) sum/10000);

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


    private void generateAsciiSumForAllContacts() {
        for (int i = 0; i < mContacts.size(); i++) {
            final Contact contact = mContacts.get(i);
            contact.generateAsciiSum();

        }
    }


}
