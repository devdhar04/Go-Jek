package com.android.gojek.viewmodel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.gojek.ContactApplication;
import com.android.gojek.R;
import com.android.gojek.data.ContactDetailService;
import com.android.gojek.model.Contact;
import com.android.gojek.utils.WebServiceConstants;
import com.android.gojek.view.AddContactActivity;
import com.bumptech.glide.Glide;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by dev on 25/04/17.
 */

public class AddContactViewModel extends BaseObservable {


    private Contact contact;
    private Context context;
    private Subscription subscription;

    public ObservableInt movieProgress;

    public AddContactViewModel(@NonNull Context context, Contact contact) {
        this.context = context;
        this.contact = contact;

        movieProgress = new ObservableInt(View.VISIBLE);


    }

    public String getFirstName() {

        return contact != null ? contact.firstName   : "";
    }

    public String getMobileNumber() {
        return contact != null ? contact.phoneNumber : "";
    }


    public String getEmail() {
        return contact != null ? contact.email : "";

    }



    public String getPictureProfile() {
        String imagePath = WebServiceConstants.BASE_URL;
        if (contact != null) {
            imagePath = imagePath + contact.contactImageUrl;
        }
        return imagePath;
    }






    private void addContact() {


        unSubscribeFromObservable();
        ContactApplication movieApplication = ContactApplication.create(context);
        ContactDetailService movieDetailService = movieApplication.getContactDetailService();
        subscription = movieDetailService.fetchContact(contact.contactDetailUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(movieApplication.subscribeScheduler())
                .subscribe(new Action1<Contact>() {
                    @Override
                    public void call(Contact movieResponse) {
                        movieProgress.set(View.GONE);

                        changeMovieDataSet(movieResponse);
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

    private void changeMovieDataSet(Contact movieResponse) {

        this.contact = movieResponse;
        notifyChange();

    }

    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


}