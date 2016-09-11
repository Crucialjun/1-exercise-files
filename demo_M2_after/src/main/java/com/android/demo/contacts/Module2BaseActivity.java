package com.android.demo.contacts;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.demo.contacts.interfaces.CallContact;
import com.android.demo.contacts.utils.CallUtils;

/**
 * Created by omrierez on 9/3/16.
 */
public abstract class Module2BaseActivity extends AppCompatActivity implements View.OnClickListener, CallContact  {

    private static final String TAG = Module2BaseActivity.class.getSimpleName();

    protected static final int MB_DIVIDER = 1000000;
    protected final Handler mHandler=new Handler();
    protected RecyclerView mRecyclerView;
    protected ImageView mIvBackground;
    protected TextView mTvRamUsage;

    abstract void loadImage();
    abstract void loadContacts();
    abstract void startRamUsageThread();
    abstract void runLoop();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module2_activity_contacts);
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
                loadContacts();
                break;

            case R.id.fab4:
                runLoop();
                break;

        }
    }

    
    @Override
    public void callNumber(String number) {
        CallUtils.callNumber(this,number);
    }

}
