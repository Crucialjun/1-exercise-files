package com.android.demo.contacts.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by omrierez on 8/31/16.
 */
public class CallUtils {

    public static void callNumber(Activity activity,String number)
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+number));
        activity.startActivity(callIntent);
    }
}
