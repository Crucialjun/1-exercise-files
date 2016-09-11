package com.android.demo.contacts.utils;

import android.content.Context;

import com.squareup.leakcanary.RefWatcher;


/**
 * Created by omrierez on 10/6/15.
 */
public class LeakCanaryManager {
    static LeakCanaryManager mInstance;
    private RefWatcher refWatcher;

    private LeakCanaryManager()
    {}
    public synchronized static LeakCanaryManager getInstance()
    {
       if (mInstance==null)
           mInstance=new LeakCanaryManager();
        return mInstance;
    }

    public void install(Context applicationContext)
    {
       // refWatcher =  LeakCanary.install(applicationContext);

    }


    public RefWatcher getRefWatcher(Context context) {
        return refWatcher;
    }

    public void leakWatch(Object obj)
    {
       // refWatcher.watch(obj);
    }

}
