package com.android.demo.m2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.demo.m2.interfaces.CallContact;
import com.android.demo.m2.mock.MockContacts;
import com.android.demo.m2.model.Contact;
import com.android.demo.m2.utils.CallUtils;

/**
 * Created by omrierez on 9/3/16.
 */
public abstract class Module3BaseActivity extends AppCompatActivity implements View.OnClickListener, CallContact  {

    private static final String TAG = Module3BaseActivity.class.getSimpleName();

    protected static final int MB_DIVIDER = 1000000;
    protected final Handler mHandler=new Handler();
    private boolean isStopped = true;
    protected RecyclerView mRecyclerView;
    protected ImageView mIvBackground;
    protected TextView mTvRamUsage;
    protected SparseArray<Contact> mContacts;

    abstract void loadImage();
    abstract void loadContacts();
    abstract void startRamUsageThread();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module3_activity_contacts);
        initViews();
    }

    private void initViews()
    {
        mRecyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.fab1).setOnClickListener(this);
        findViewById(R.id.fab2).setOnClickListener(this);
        findViewById(R.id.fab3).setOnClickListener(this);
        findViewById(R.id.fab4).setOnClickListener(this);
        mIvBackground=(ImageView)findViewById(R.id.ivbackground);
        mTvRamUsage=(TextView)findViewById(R.id.tvRamUsage);


    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.fab1:
                loadImage();
                break;
            case R.id.fab2:
                startRamUsageThread();
                break;
            case R.id.fab3:
                fetchContacts();
                break;
            case R.id.fab4:
                if (isStopped)
                    startService();
                else
                    stopService();
                isStopped = !isStopped;
                break;
        }
    }



    private void fetchContacts() {
        mContacts= MockContacts.mockSpareContacts();
       // mContacts= ContactsUtils.betterSparseFetchAllContacts(this);
        generateAsciiSumForAllContacts();
        loadContacts();
    }

    private void generateAsciiSumForAllContacts()
    {
        for (int i = 0; i <mContacts.size() ; i++) {
            final Contact contact=mContacts.get(i);
            contact.generateAsciiSum();

        }
    }

    private void startService() {
        Log.d(TAG, "startService() called");
        startService(new Intent(this, M3BeforeSensorService.class));
    }

    private void stopService() {
        Log.d(TAG, "stopService() called");
        stopService(new Intent(this, M3BeforeSensorService.class));
    }



    @Override
    public void callNumber(String number) {
        CallUtils.callNumber(this,number);
    }

}
