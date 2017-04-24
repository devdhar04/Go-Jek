package com.android.gojek.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;


import com.android.gojek.ContactApplication;
import com.android.gojek.R;
import com.android.gojek.data.ContactListResponse;
import com.android.gojek.data.ContactListService;
import com.android.gojek.model.Contact;
import com.android.gojek.utils.WebServiceConstants;

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

    private Contact[] contactList;
    private Context context;
    private Subscription subscription;

    public ContactListViewModel(@NonNull Context context) {

        this.context = context;

        contactProgress = new ObservableInt(View.GONE);
        contactRecycler = new ObservableInt(View.GONE);
        contactLabel = new ObservableInt(View.VISIBLE);

        initializeViews();
        fetchContactList();

    }


    public void initializeViews() {
        contactLabel.set(View.GONE);
        contactRecycler.set(View.VISIBLE);
        contactProgress.set(View.VISIBLE);

    }


    private void changeContactDataSet(Contact[] contacts) {
        this.contactList = contacts;
        //contactList.addAll(contacts);
        setChanged();
        notifyObservers();
    }

    public Contact[] getContactList() {
        return contactList;
    }

    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void fetchContactList() {

        final String URL = WebServiceConstants.CONTACT_LIST_URL;
        unSubscribeFromObservable();
        ContactApplication movieApplication = ContactApplication.create(context);
        ContactListService movieListService = movieApplication.getContactListService();
        subscription = movieListService.fetchContacts(URL)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(movieApplication.subscribeScheduler())
                .subscribe(new Action1<Contact[]>() {
                    @Override
                    public void call(Contact[] contactResponse) {
                        contactProgress.set(View.GONE);
                        contactLabel.set(View.GONE);
                        contactRecycler.set(View.VISIBLE);
                        Log.v("Response ","List "+contactResponse);
                        changeContactDataSet(contactResponse);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        messageLabel.set(context.getString(R.string.error_loading_contacts));
                        //movieProgress.set(View.GONE);
                        //movieLabel.set(View.VISIBLE);
                        //movieRecycler.set(View.GONE);
                    }
                });
    }

    public void reset() {
        unSubscribeFromObservable();
        subscription = null;
        context = null;
    }
}