package com.android.gojek.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    String color[] = {"#40A189","#324AF9","#B8251E","#4BB341","#EB6426","#8333FC" };

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
    static int index=0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GradientDrawable getDrawable()
    {
        GradientDrawable bgShape = (GradientDrawable) context.getResources().getDrawable(R.drawable.cicular_shape,context.getTheme());
        bgShape.setColor(Color.parseColor(color[index]));
        if(index<color.length-1) {
            index = index + 1;
        }
        else
        {
            index =0;
        }
     return bgShape;
    }
    @BindingAdapter("drawable")
    public static void setDrawable(TextView textView,Drawable drawable)
    {
        textView.setBackgroundDrawable(drawable);

    }


    @BindingAdapter("favourite")
    public static void setFavourite(ImageView imageView,boolean isFavourite)
    {
        if(isFavourite) {
            imageView.setImageResource(R.mipmap.ic_star);
        }
        else{
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
