package com.android.gojek.viewmodel;

import java.util.Observable;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;


import com.android.gojek.ContactApplication;
import com.android.gojek.R;
import com.android.gojek.data.ContactApiService;
import com.android.gojek.db.ContactHelper;
import com.android.gojek.model.Contact;
import com.android.gojek.utils.WebServiceConstants;
import com.android.gojek.view.AddContactActivity;

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
    private ProgressDialog progress;
    private Contact[] contactList;
    private Context context;
    private Subscription subscription;

    public ContactListViewModel(@NonNull Context context) {

        this.context = context;

        contactProgress = new ObservableInt(View.GONE);
        contactRecycler = new ObservableInt(View.GONE);
        contactLabel = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<String>();
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
        ContactApplication contactApplication = ContactApplication.create(context);
        ContactApiService movieApiService = contactApplication.getContactApiService();
        subscription = movieApiService.fetchContacts(URL)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(contactApplication.subscribeScheduler())
                .subscribe(new Action1<Contact[]>() {
                    @Override
                    public void call(Contact[] contactResponse) {

                        saveToDb(contactResponse);
                        showProgressDialog();
                        //contactProgress.set(View.GONE);
                        contactLabel.set(View.GONE);
                        contactRecycler.set(View.VISIBLE);

                        changeContactDataSet(contactResponse);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        messageLabel.set(context.getString(R.string.error_loading_contacts));
                        contactLabel.set(View.VISIBLE);
                        if (progress != null && progress.isShowing()) {
                            progress.cancel();
                        }

                    }
                });
    }


    private void showProgressDialog() {
        progress = new ProgressDialog(context);
        progress.setMessage("Please wait) ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
    }

    private void saveToDb(Contact[] contactResponse) {
        for (Contact contact : contactResponse) {
            ContactHelper.getInstance(context).add(contact);

        }
    }

    public void onClick(View view) {
        context.startActivity(AddContactActivity.launchDetail(view.getContext(), null));
    }


    public void reset() {
        unSubscribeFromObservable();
        subscription = null;
        context = null;
    }
}