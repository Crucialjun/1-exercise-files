package com.android.demo.contacts;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.demo.contacts.model.Contact;

import java.util.ArrayList;

public class UnhappyContactsActivity extends AppCompatActivity implements UnhappyContactsAdapter.CallContact {
    private static final String TAG = UnhappyContactsActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private UnhappyContactsAdapter mAdapter;
    private FloatingActionButton mFab;
    private ImageView mIvBackground;
    private TextView mTvRamUsage;
    //problem 2.3
    private ArrayList<Contact> mContacts;
    private final Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        startRamUsageThread();


    }

    private void initViews()
    {
        mRecyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new UnhappyContactsAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);
        mFab=(FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //problem 2.4
                mIvBackground.setBackgroundResource(R.drawable.background);
                fetchContacts();
            }
        });
        mIvBackground=(ImageView)findViewById(R.id.ivbackground);
        mTvRamUsage=(TextView)findViewById(R.id.tvRamUsage);


    }

    private void startRamUsageThread()
    {
        //problem 3.2
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    while(true) {
                        sleep(20);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //problem 2.2
                                String ramUsage = String.format("%.3f MB, free:%.3f MB", ((float)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000), ((float)Runtime.getRuntime().freeMemory())/1000000);
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

    private void fetchContacts() {
        mContacts=MockContacts.mockContacts();
        //mContacts=fetchAllContacts();

        generateAsciiSumForAllContacts();
        mAdapter.addItems(mContacts);

    }


    private ArrayList<Contact> fetchAllContacts()
    {
        //problem 2.2
        Log.d(TAG, "fetchAllContacts() start");
        ArrayList<Contact> contacts=new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                String uri = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    String phoneNo;
                    while (pCur.moveToNext()) {
                        phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contacts.add(new Contact(name,phoneNo));

                    }
                    pCur.close();
                }
            }
        }
        Log.d(TAG, "fetchAllContacts() finish");
        return contacts;

    }
    private void generateAsciiSumForAllContacts()
    {
        for (int i = 0; i <mContacts.size() ; i++) {
            final Contact contact=mContacts.get(i);
            contact.generateAsciiSum();

        }
    }

    @Override
    public void callNumber(String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+number));
        startActivity(callIntent);
    }

}
