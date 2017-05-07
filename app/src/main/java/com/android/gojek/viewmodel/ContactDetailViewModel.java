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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.gojek.ContactApplication;
import com.android.gojek.R;
import com.android.gojek.data.ContactApiService;
import com.android.gojek.model.Contact;
import com.android.gojek.utils.WebServiceConstants;

import com.android.gojek.view.AddContactActivity;
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

    private String contactDetailUrl = "";
    public ContactDetailViewModel(@NonNull Context context, Contact contact) {
        this.context = context;
        this.contact = contact;

        movieProgress = new ObservableInt(View.VISIBLE);

        fetchContactDetails();


    }

    public String getTitle() {

        return contact != null ? contact.firstName + " " + contact.lastName : "";
    }

    public String getMobileNumber() {
        return contact != null ? contact.phoneNumber : "";
    }


    public String getEmail() {
        return contact != null ? contact.email : "";

    }

    public void clickEdit()
    {
        context.startActivity(AddContactActivity.launchDetail(context, contact));
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_phone:
                callNumber();
                break;
            case R.id.image_sms:
                sendSMS();
                break;
            case R.id.image_email:
                sendEmail();
                break;


        }
    }

    private void callNumber() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+contact.phoneNumber));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(callIntent);
    }

    private void sendSMS()
    {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                + contact.phoneNumber)));
    }

    private void sendEmail()
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{contact.email});

        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();

        }
    }

    public String getPictureProfile() {
        String imagePath = WebServiceConstants.BASE_URL;
        if (contact != null) {
            imagePath = imagePath + contact.contactImageUrl;
        }
        return imagePath;
    }





    private void fetchContactDetails() {

        contactDetailUrl = contact.contactDetailUrl;
        unSubscribeFromObservable();
        ContactApplication movieApplication = ContactApplication.create(context);
        ContactApiService ContactApiService = movieApplication.getContactApiService();
        subscription = ContactApiService.fetchContact(contactDetailUrl)
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

     private void setFavouriteIcon(Menu menu)
     {
         if(contact.isFavorite) {
             menu.getItem(0).setIcon(context.getResources().getDrawable(R.mipmap.ic_favourite_filled));
         }
         else
         {
             menu.getItem(0).setIcon(context.getResources().getDrawable(R.mipmap.ic_favourite));
         }
     }

    public void updateContact(final Menu menu) {

        contact.setFavorite(!contact.isFavorite);
        movieProgress.set(View.VISIBLE);
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
                        setFavouriteIcon(menu);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();

                        movieProgress.set(View.GONE);

                    }
                });


    }
    public void deleteContact() {

        movieProgress.set(View.VISIBLE);

        unSubscribeFromObservable();
        ContactApplication movieApplication = ContactApplication.create(context);
        ContactApiService ContactApiService = movieApplication.getContactApiService();
        subscription = ContactApiService.deleteContact(contactDetailUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(movieApplication.subscribeScheduler())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String movieResponse) {
                        movieProgress.set(View.GONE);

                        ((AppCompatActivity)context).finish();
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
