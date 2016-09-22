package com.android.demo.m2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.demo.m2.interfaces.CallContact;
import com.android.demo.m2.model.Contact;

/**
 * Created by omrierez on 25/08/16.
 */
public class M3BeforeContactsAdapter extends RecyclerView.Adapter<M3BeforeContactsAdapter.ContactViewHolder> {

    private static final String TAG = "ContactsAdapter";

    private SparseArray<Contact> mItems = new SparseArray<>();
    private CallContact mCallContact;
    //problem 3.1: will leak an activity on configuration change;
    private static Context mContext;
    private LayoutInflater mLi;
    public M3BeforeContactsAdapter(Context activityContext, CallContact callContact) {
        mContext=activityContext;
        mCallContact=callContact;
        mLi=((AppCompatActivity)mContext).getLayoutInflater();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(mLi.inflate(R.layout.list_item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, int position) {
        long startTime=System.currentTimeMillis();
        final Contact contact = mItems.get(position);
        holder.mTvName.setText(contact.getName());
        holder.mTvNumber.setText(contact.getNumber());
        holder.mTvAsciiSum.setText(contact.getAsciiSum());
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallContact.callNumber(holder.mTvNumber.getText().toString());
            }
        });
        Log.d(TAG, String.format("onBindViewHolder() took : %d",(System.currentTimeMillis()-startTime)));


    }


    public void addItems(SparseArray<Contact> contacts) {
        mItems.clear();
        mItems = contacts;
        notifyDataSetChanged();
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private CardView mCard;
        private TextView mTvName;
        private TextView mTvNumber;
        private TextView mTvAsciiSum;

        public ContactViewHolder(View itemView) {
            super(itemView);
            mCard = (CardView) itemView.findViewById(R.id.card);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            mTvAsciiSum = (TextView) itemView.findViewById(R.id.tvAsciiSum);
        }
    }



}
