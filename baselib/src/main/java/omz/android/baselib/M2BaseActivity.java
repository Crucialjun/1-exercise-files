package omz.android.baselib;

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
public abstract class M2BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = M2BaseActivity.class.getSimpleName();


    protected final Handler mHandler = new Handler();
    protected ImageView mIvBackground;
    protected TextView mTvRamUsage;

    //Core methods to be invoked by clicking on the fabs

    protected abstract void runLoop();              //2.1

    protected abstract void startRamUsage();  //2.2

    protected abstract void loadImage();            //2.3

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        simpleAllocation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        initViews();
    }

    private void simpleAllocation() {
        Object obj = new Object();
        Float f = new Float(2.22);
        Double d = new Double(11d);
        int intPrim = 123;
        double doublePrim = 1111d;
        String s1 = "1";
        String s2 = "12";
        String s3 = "123";
        String s4 = "1234";
        String s5 = "12345";
    }

    private void initViews() {
        findViewById(R.id.fab1).setOnClickListener(this);
        findViewById(R.id.fab2).setOnClickListener(this);
        findViewById(R.id.fab3).setOnClickListener(this);
        mIvBackground = (ImageView) findViewById(R.id.ivbackground);
        mTvRamUsage = (TextView) findViewById(R.id.tvRamUsage);


    }

    @Override
    public void onClick(View view) {

        final int resId = view.getId();
        if (resId == R.id.fab1)
            startRamUsage();
        else if (resId == R.id.fab2)
            loadImage();
        else if (resId == R.id.fab3)
            runLoop();
    }

    protected void sleepDelay() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
