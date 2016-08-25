package com.android.demo.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.demo.contacts.model.Contact;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {
    private static final String TAG = ContactsActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ContactsAdapter mAdapter;
    private ArrayList<Contact> mContacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupList();
        mContacts=fetchAllContacts();
        generateAsciiSumForAllContacts();
        mAdapter.addItems(mContacts);




    }

    private void setupList()
    {
        mRecyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new ContactsAdapter(getLayoutInflater());
        mRecyclerView.setAdapter(mAdapter);


    }


    private ArrayList<Contact> fetchAllContacts()
    {
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
        for (Contact contact:mContacts)
            contact.generateAsciiSum();
    }

}
