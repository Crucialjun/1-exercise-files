package com.android.demo.contacts;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.demo.contacts.model.Contact;

import java.util.ArrayList;

/**
 * Created by omrierez on 25/08/16.
 */
public class UnhappyContactsAdapter extends RecyclerView.Adapter<UnhappyContactsAdapter.ContactViewHolder> {

    private ArrayList<Contact> mItems = new ArrayList<>();
    private LayoutInflater mLi;
    private CallContact mCallContact;
    //problem 3.1
    private static Context mContext;

    public UnhappyContactsAdapter(Context context, CallContact callContact) {
        mContext=context;
        mLi = ((AppCompatActivity)mContext).getLayoutInflater();
        mCallContact=callContact;
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

    }


    public void addItems(ArrayList<Contact> contacts) {
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


    public interface CallContact
    {
        void callNumber(String number);
    }
}
