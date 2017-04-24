package com.android.gojek;

import android.app.Application;
import android.content.Context;

import com.android.gojek.data.ContactDetailService;
import com.android.gojek.data.ContactListService;
import com.android.gojek.data.ContactsFactory;


import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by dev on 16/04/17.
 */

public class ContactApplication extends Application {

    private ContactListService contactListService;
    private ContactDetailService contactDetailService;
    private Scheduler scheduler;

    private static ContactApplication get(Context context) {
        return (ContactApplication) context.getApplicationContext();
    }

    public static ContactApplication create(Context context) {
        return ContactApplication.get(context);
    }

    public ContactListService getContactListService() {
        if (contactListService == null) contactListService = ContactsFactory.create();

        return contactListService;
    }

    public ContactDetailService getContactDetailService() {
        if (contactDetailService == null) contactDetailService = ContactsFactory.createDetailService();

        return contactDetailService;
    }
    public Scheduler subscribeScheduler() {
        if (scheduler == null) scheduler = Schedulers.io();

        return scheduler;
    }

    public void setContactListService(ContactListService contactListService) {
        this.contactListService = contactListService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}