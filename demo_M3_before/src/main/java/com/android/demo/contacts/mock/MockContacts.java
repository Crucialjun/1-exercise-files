package com.android.demo.m2.mock;

import android.util.SparseArray;

import com.android.demo.m2.model.Contact;

import java.util.ArrayList;

/**
 * Created by omrierez on 8/25/16.
 */
public class MockContacts {



    public static ArrayList<Contact> mockListContacts()
    {
        ArrayList<Contact> contacts=new ArrayList<>();
        Contact contact;
        for (int i = 0; i <1000 ; i++) {
            contact=new Contact("Pluralsight","0187426876");
            contact.generateAsciiSum();
            contacts.add(contact);
        }
        return contacts;
    }

    public static SparseArray<Contact> mockSpareContacts()
    {
        SparseArray<Contact> contacts=new SparseArray<>();
        Contact contact;
        for (int i = 0; i <1000 ; i++) {
            contact=new Contact("Pluralsight","0187426876");
            contact.generateAsciiSum();
            contacts.append(i,contact);
        }
        return contacts;
    }
}
