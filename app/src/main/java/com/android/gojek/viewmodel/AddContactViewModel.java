package com.android.gojek.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.gojek.BR;
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
    public ObservableInt firstNameVisibility;
    public ObservableInt lastNameVisibility;
    public ObservableInt mobileVisibility;
    public ObservableInt emailVisibility;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public AddContactViewModel(@NonNull Context context, Contact contact) {
        this.context = context;
        this.contact = contact;

        movieProgress = new ObservableInt(View.GONE);
        firstNameVisibility = new ObservableInt(View.GONE);
        lastNameVisibility = new ObservableInt(View.GONE);
        mobileVisibility = new ObservableInt(View.GONE);
        emailVisibility = new ObservableInt(View.GONE);


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




    public String getPictureProfile() {
        String imagePath = WebServiceConstants.BASE_URL;
        if (contact != null) {
            imagePath = imagePath + contact.contactImageUrl;
        }
        return imagePath;
    }


    private boolean checkValidation(Contact con)
    {
        if(con.firstName.length()<3)
        {
            firstNameVisibility.set(View.VISIBLE);
            return false;
        }
        else if(con.lastName.length()<3)
        {
            lastNameVisibility.set(View.VISIBLE);
            firstNameVisibility.set(View.INVISIBLE);
            return false;
        }
        else if(con.phoneNumber.length()<10)
        {
            mobileVisibility.set(View.VISIBLE);
            lastNameVisibility.set(View.INVISIBLE);
            firstNameVisibility.set(View.INVISIBLE);
            return false;
        }
        else if(!con.email.matches(emailPattern))
        {
            emailVisibility.set(View.VISIBLE);
            mobileVisibility.set(View.INVISIBLE);
            lastNameVisibility.set(View.INVISIBLE);
            firstNameVisibility.set(View.INVISIBLE);
            return false;
        }
    return true;
    }


    public void updateContact(Contact con) {
        if(!checkValidation(con))
        {
            return ;
        }
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
                        Toast.makeText(context,"Network Error Occur !",Toast.LENGTH_SHORT).show();
                        movieProgress.set(View.GONE);

                    }
                });
    }



    public void addContact(Contact con) {
        if(!checkValidation(con))
        {
            return ;
        }
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
                        Toast.makeText(context,"Network Error Occur !",Toast.LENGTH_SHORT).show();
                        movieProgress.set(View.GONE);

                    }
                });
    }




    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }



    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


}