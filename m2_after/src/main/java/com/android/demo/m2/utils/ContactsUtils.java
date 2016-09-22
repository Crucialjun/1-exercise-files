package com.android.demo.m2.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseArray;

import com.android.demo.m2.model.Contact;

import java.util.ArrayList;

/**
 * Created by omrierez on 8/31/16.
 */
public class ContactsUtils {

    private static final String TAG = ContactsUtils.class.getSimpleName();

    public static ArrayList<Contact> poorFetchAllContacts(Context appContext)
    {
        Log.d(TAG, "fetchAllContacts() start");
        ArrayList<Contact> contacts=new ArrayList<>();
        ContentResolver cr = appContext.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
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

    public static ArrayList<Contact> betterFetchAllContacts(Context appContext)
    {
        Log.d(TAG, "fetchAllContacts() start");
        ArrayList<Contact> contacts=new ArrayList<>();
        ContentResolver cr = appContext.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        /////////////2.3   Create one reference instead of a new reference for each iteration of the loop
        String id,name,phoneNo;
        Cursor pCur;
        int hasPhoneNumber;
        int nameColumnIndex,hasPhoneColumnIndex,idColumnIndex;
        //////////////////////////////////////////////////////////////////////////////////////////////////

        if (cur.getCount() > 0) {
            nameColumnIndex=cur.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME);
            idColumnIndex=cur.getColumnIndex(ContactsContract.Contacts._ID);
            hasPhoneColumnIndex=cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            while (cur.moveToNext()) {
                id = cur.getString(idColumnIndex);
                name = cur.getString(nameColumnIndex);
                hasPhoneNumber=cur.getInt(hasPhoneColumnIndex);
                if (hasPhoneNumber> 0) {
                    pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
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

    public static SparseArray<Contact> betterSparseFetchAllContacts(Context appContext)
    {
        Log.d(TAG, "fetchAllContacts() start");
        SparseArray<Contact> contacts=new SparseArray<>();
        ContentResolver cr = appContext.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        /////////////2.3   Create one reference instead of a new reference for each iteration of the loop
        String id,name,phoneNo;
        Cursor pCur;
        int hasPhoneNumber;
        int nameColumnIndex,hasPhoneColumnIndex,idColumnIndex;
        //////////////////////////////////////////////////////////////////////////////////////////////////

        if (cur.getCount() > 0) {
            nameColumnIndex=cur.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME);
            idColumnIndex=cur.getColumnIndex(ContactsContract.Contacts._ID);
            hasPhoneColumnIndex=cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            while (cur.moveToNext()) {
                id = cur.getString(idColumnIndex);
                name = cur.getString(nameColumnIndex);
                hasPhoneNumber=cur.getInt(hasPhoneColumnIndex);
                if (hasPhoneNumber> 0) {
                    pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contacts.append(contacts.size(),new Contact(name,phoneNo));

                    }
                    pCur.close();
                }
            }
        }
        Log.d(TAG, "fetchAllContacts() finish");
        return contacts;

    }


}
