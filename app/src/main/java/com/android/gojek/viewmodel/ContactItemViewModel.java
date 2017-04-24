package com.android.gojek.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.android.gojek.model.Contact;
import com.android.gojek.utils.WebServiceConstants;
import com.bumptech.glide.Glide;

/**
 * Created by dev on 23/04/17.
 */

public class ContactItemViewModel extends BaseObservable {

    private Contact contact;
    private Context context;

    public ContactItemViewModel(Contact contact, Context context) {
        this.contact = contact;
        this.context = context;
    }

    public String getFullName() {

        return contact.firstName + " " + contact.lastName;
    }


    public String getPictureProfile() {
        return WebServiceConstants.CONTACT_IMAGE_URL;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public void onItemClick(View view) {
        //context.startActivity(MovieDetailActivity.launchDetail(view.getContext(), contact));
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        notifyChange();
    }
}
