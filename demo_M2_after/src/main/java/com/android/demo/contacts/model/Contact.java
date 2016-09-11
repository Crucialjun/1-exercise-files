package com.android.demo.contacts.model;

/**
 * Created by omrierez on 25/08/16.
 */
public class Contact {

    private String mName;
    private String mNumber;
    private long mAsciiSum;


    public Contact(String mName, String mNumber) {
        this.mName = mName;
        this.mNumber = mNumber;
    }

    public String getName() {
        return mName;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getAsciiSum() {
        return String.valueOf(mAsciiSum);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "mName='" + mName + '\'' +
                ", mNumber='" + mNumber + '\'' +
                '}';
    }
    public void generateAsciiSum()
    {
        for (int i = 0; i < mName.length(); i++) {
            char c=mName.charAt(i);
            mAsciiSum+=(int)c;
            }

    }
}
