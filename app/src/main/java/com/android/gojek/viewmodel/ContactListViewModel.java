package com.android.gojek.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;


import com.android.gojek.model.Contact;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by dev on 22/04/17.
 */

public class ContactListViewModel extends Observable {

    public ObservableInt contactProgress;
    public ObservableInt contactRecycler;
    public ObservableInt contactLabel;
    public ObservableField<String> messageLabel;

    private List<Contact> contactList;
    private Context context;
    private Subscription subscription;

    public ContactListViewModel(@NonNull Context context) {

        this.context = context;
        this.contactList = new ArrayList<>();
        contactProgress = new ObservableInt(View.GONE);
        contactRecycler = new ObservableInt(View.GONE);
        contactLabel = new ObservableInt(View.VISIBLE);

        initializeViews();

    }


    public void initializeViews() {
        contactLabel.set(View.GONE);
        contactRecycler.set(View.GONE);
        contactProgress.set(View.VISIBLE);

    }



    private void changeContactDataSet(List<Contact> contacts) {
        contactList.addAll(contacts);
        setChanged();
        notifyObservers();
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        subscription = null;
        context = null;
    }
}