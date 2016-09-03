package com.android.demo.contacts.module2;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.demo.contacts.R;
import com.android.demo.contacts.interfaces.CallContact;
import com.android.demo.contacts.model.Contact;

/**
 * Created by omrierez on 25/08/16.
 */
public class Module2HappyContactsAdapter extends RecyclerView.Adapter<Module2HappyContactsAdapter.ContactViewHolder> {
    private static final String TAG = "ContactsAdapter";
    //2.4   use optimized containers instead of the common ArrayLists and HashMaps
    private SparseArray<Contact> mItems = new SparseArray<>();
    private CallContact mCallContact;
    private LayoutInflater mLi;
    private  Contact mContactTemp;

    public Module2HappyContactsAdapter(LayoutInflater layoutInflater, CallContact callContact) {
        mLi = layoutInflater;
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
        long startTime=System.nanoTime();
        mContactTemp = mItems.get(position);
        holder.mTvName.setText(mContactTemp.getName());
        holder.mTvNumber.setText(mContactTemp.getNumber());
        holder.mTvAsciiSum.setText(mContactTemp.getAsciiSum());
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallContact.callNumber(holder.mTvNumber.getText().toString());
            }
        });
        Log.d(TAG, String.format("onBindViewHolder() took : %d",(System.nanoTime()-startTime)));
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
