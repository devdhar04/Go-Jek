package com.android.gojek.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.gojek.R;
import com.android.gojek.databinding.ContactItemBinding;
import com.android.gojek.model.Contact;
import com.android.gojek.viewmodel.ContactItemViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by dev on 23/04/17.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder> {

    private List<Contact> contactList;

    public ContactListAdapter() {
        this.contactList = Collections.emptyList();
    }

    @Override
    public ContactListAdapter.ContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContactItemBinding contactItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.contact_item,
                        parent, false);
        return new ContactListViewHolder(contactItemBinding);
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ContactListViewHolder holder, int position) {
        holder.bindContact(contactList.get(position));
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void setContactList(List<Contact> movieList) {
        this.contactList = movieList;
        notifyDataSetChanged();
    }

    public static class ContactListViewHolder extends RecyclerView.ViewHolder {
        ContactItemBinding mContactItemBinding;

        public ContactListViewHolder(ContactItemBinding contactItemBinding) {

            super(contactItemBinding.itemContact);
            this.mContactItemBinding = contactItemBinding;
        }

        void bindContact(Contact contact) {
            if (mContactItemBinding.getContactItemViewModel() == null) {
                mContactItemBinding.setContactItemViewModel(
                        new ContactItemViewModel(contact, itemView.getContext()));
            } else {
                mContactItemBinding.getContactItemViewModel().setContact(contact);
            }
        }
    }
}