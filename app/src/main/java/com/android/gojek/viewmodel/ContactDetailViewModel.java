package com.android.gojek.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.android.gojek.ContactApplication;
import com.android.gojek.data.ContactDetailService;
import com.android.gojek.model.Contact;
import com.android.gojek.utils.WebServiceConstants;

import com.bumptech.glide.Glide;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by dev on 16/04/17.
 */

public class ContactDetailViewModel extends BaseObservable {


    private Contact contact;
    private Context context;
    private Subscription subscription;

    public ObservableInt movieProgress;

    public ContactDetailViewModel(@NonNull Context context, Contact contact) {
        this.context = context;
        this.contact = contact;

        movieProgress = new ObservableInt(View.VISIBLE);
        fetchContactDetails();

    }

    public String getTitle() {

        return contact != null ? contact.firstName+" "+contact.lastName : "";
    }

    public String getMobileNumber() {
        return contact != null ? contact.phoneNumber : "";
    }



    public String getEmail() {
        return contact != null ? contact.email:"";

    }





    public String getPictureProfile() {
        String imagePath = WebServiceConstants.BASE_URL;
        if (contact != null) {
            imagePath = imagePath + contact.contactImageUrl;
        }
        return imagePath;
    }



    public void onItemClick(View view) {
        //Intent intent = new Intent(context, WebViewActivity.class);
        //context.startActivity(intent);
    }


    private void fetchContactDetails() {


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
