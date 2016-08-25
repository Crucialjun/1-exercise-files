package com.android.demo.contacts;

import com.android.demo.contacts.model.Contact;

import java.util.ArrayList;

/**
 * Created by omrierez on 8/25/16.
 */
public class MockContacts {



    public static ArrayList<Contact> mockContacts()
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
}
