package com.android.gojek.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.android.gojek.ContactApplication;
import com.android.gojek.data.ContactApiService;
import com.android.gojek.model.Contact;
import com.android.gojek.utils.WebServiceConstants;
import com.android.gojek.view.AddContactActivity;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by dev on 25/04/17.
 */

public class AddContactViewModel extends BaseObservable {

    public static final int PICK_IMAGE_REQUEST = 1;
    private String mCurrentPhotoPath;
    private Contact contact;
    private Context context;
    private Subscription subscription;

    public ObservableInt movieProgress;

    public AddContactViewModel(@NonNull Context context, Contact contact) {
        this.context = context;
        this.contact = contact;

        movieProgress = new ObservableInt(View.GONE);


    }

    public String getFirstName() {

        return contact != null ? contact.firstName   : "";
    }

    public String getLastName() {

        return contact != null ? contact.lastName   : "";
    }

    public String getMobileNumber() {
        return contact != null ? contact.phoneNumber : "";
    }


    public String getEmail() {
        return contact != null ? contact.email : "";

    }



    public void clickPicture(View v) {

        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        ((AddContactActivity)context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }


    public int getFirstNameVisibility()
    {
        int visbile = View.INVISIBLE;
        if(contact.firstName != null && contact.firstName.trim().length()>=3)
        {
            visbile =  View.VISIBLE;
        }

        return visbile;
    }

    public String getPictureProfile() {
        String imagePath = WebServiceConstants.BASE_URL;
        if (contact != null) {
            imagePath = imagePath + contact.contactImageUrl;
        }
        return imagePath;
    }



    public void updateContact(Contact con) {
        movieProgress.set(View.VISIBLE);
        contact.setFirstName(con.firstName);
        contact.setLastName(con.lastName);
        contact.setEmail(con.email);
        contact.setPhoneNumber(con.phoneNumber);
        String url = WebServiceConstants.CONTACT_ADD_URL+contact.id+".json";

        unSubscribeFromObservable();
        ContactApplication contactApplication = ContactApplication.create(context);
        ContactApiService movieDetailService = contactApplication.getContactApiService();
        subscription = movieDetailService.updateContact(url,contact)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(contactApplication.subscribeScheduler())
                .subscribe(new Action1<Contact>() {
                    @Override
                    public void call(Contact contact) {
                        movieProgress.set(View.GONE);
                        ((AddContactActivity)context).finish();
                      //  changeContactDataSet(contact);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();

                        movieProgress.set(View.GONE);

                    }
                });
    }

    public void addContact(Contact con) {

        movieProgress.set(View.VISIBLE);
        String url = WebServiceConstants.CONTACT_LIST_URL;

        unSubscribeFromObservable();
        ContactApplication contactApplication = ContactApplication.create(context);
        ContactApiService movieDetailService = contactApplication.getContactApiService();
        subscription = movieDetailService.addContact(url,con)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(contactApplication.subscribeScheduler())
                .subscribe(new Action1<Contact>() {
                    @Override
                    public void call(Contact contact) {
                        movieProgress.set(View.GONE);
                        ((AddContactActivity)context).finish();
                        //  changeContactDataSet(contact);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();

                        movieProgress.set(View.GONE);

                    }
                });
    }




    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }

    private void changeContactDataSet(Contact movieResponse) {

        this.contact = movieResponse;
        notifyChange();

    }

    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


}