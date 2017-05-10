package com.android.gojek;

import android.app.Application;
import android.content.Context;

import com.android.gojek.data.ContactApiService;
import com.android.gojek.data.ContactsFactory;


import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by dev on 16/04/17.
 */

public class ContactApplication extends Application {


    private ContactApiService contactApiService;

    private Scheduler scheduler;

    private static ContactApplication get(Context context) {
        return (ContactApplication) context.getApplicationContext();
    }

    public static ContactApplication create(Context context) {
        return ContactApplication.get(context);
    }


    public ContactApiService getContactApiService() {
        if (contactApiService == null) contactApiService = ContactsFactory.createAddService();

        return contactApiService;
    }


    public Scheduler subscribeScheduler() {
        if (scheduler == null) scheduler = Schedulers.io();

        return scheduler;
    }


    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}