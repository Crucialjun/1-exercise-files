package com.android.demo.m2;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by omrierez on 9/3/16.
 */
public abstract class Module2BaseActivity extends AppCompatActivity implements View.OnClickListener  {

    private static final String TAG = Module2BaseActivity.class.getSimpleName();

    protected static final int MB_DIVIDER = 1000000;
    protected final Handler mHandler=new Handler();
    protected ImageView mIvBackground;
    protected TextView mTvRamUsage;

    abstract void loadImage();
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
                break;
            case R.id.fab4:
                runLoop();
                break;
        }
    }

    

}
