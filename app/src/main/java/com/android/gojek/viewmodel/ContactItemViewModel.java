package com.android.gojek.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.gojek.R;
import com.android.gojek.model.Contact;
import com.android.gojek.utils.WebServiceConstants;
import com.android.gojek.view.ContactDetailActivity;
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

    public String getContactFirstChar() {

        return String.valueOf(contact.firstName.toUpperCase().charAt(0));
    }
    public boolean getFavourite()
    {
        return contact.isFavorite;
    }

    @BindingAdapter("favourite")
    public static void setFavourite(ImageView imageView,boolean isFavourite)
    {
        if(isFavourite) {
            imageView.setImageResource(R.mipmap.ic_star);
        }
        {
            imageView.setImageResource(R.mipmap.ic_favourite);
        }

    }
    public void onItemClick(View view) {
        context.startActivity(ContactDetailActivity.launchDetail(view.getContext(), contact));
    }


    public String getPictureProfile() {
        return WebServiceConstants.BASE_URL+contact.contactImageUrl;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }



    public void setContact(Contact contact) {
        this.contact = contact;
        notifyChange();
    }
}
