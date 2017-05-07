package com.android.gojek.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.gojek.R;
import com.android.gojek.databinding.ContactDetailBinding;
import com.android.gojek.model.Contact;
import com.android.gojek.viewmodel.ContactDetailViewModel;

import retrofit2.http.HTTP;

/**
 * Created by dev on 24/04/17.
 */

public class ContactDetailActivity extends AppCompatActivity {

    private static final String EXTRA_URL = "DETAIL_URL";
    private static final String EXTRA_FIRST_NAME = "CONTACT_FIRST_NAME";
    private static final String EXTRA_LAST_NAME = "CONTACT_LAST_NAME";
    private static final String EXTRA_IMAGE = "IMAGE_URL";

    private ContactDetailBinding movieDetailActivityBinding;
    private static boolean isFavourite = false;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDetailActivityBinding =
                DataBindingUtil.setContentView(this, R.layout.contact_detail);
        setSupportActionBar(movieDetailActivityBinding.toolbar);
        getExtrasFromIntent();
    }

    public static Intent launchDetail(Context context, Contact contact) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        if(contact != null)
        {    isFavourite =  contact.isFavorite;
            intent.putExtra(EXTRA_URL,contact.contactDetailUrl);
            intent.putExtra(EXTRA_FIRST_NAME,contact.firstName);
            intent.putExtra(EXTRA_LAST_NAME,contact.lastName);
            intent.putExtra(EXTRA_IMAGE,contact.contactDetailUrl);
        }

        return intent;
    }

    Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_detail, menu);
        this.menu = menu;
        setFavouriteIcon(menu);
        return true;
    }

    private void setFavouriteIcon(Menu menu)
    {
        if(isFavourite) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.mipmap.ic_favourite_filled));
        }
        else
        {
            menu.getItem(0).setIcon(getResources().getDrawable(R.mipmap.ic_favourite));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            movieDetailActivityBinding.getContactDetailViewModel().clickEdit();
            return true;
        }
        else  if (id == R.id.action_favourite) {

            movieDetailActivityBinding.getContactDetailViewModel().updateContact(menu);

            return true;
        }
        else if(id == R.id.action_delete)
        {
            movieDetailActivityBinding.getContactDetailViewModel().deleteContact();
         return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getExtrasFromIntent() {

         Contact contact = new Contact();
         if(getIntent() != null) {
             contact.setFirstName(getIntent().getStringExtra(EXTRA_FIRST_NAME));
             contact.setLastName(getIntent().getStringExtra(EXTRA_LAST_NAME));
             contact.setContactDetailUrl(getIntent().getStringExtra(EXTRA_URL));
             contact.setContactImageUrl(getIntent().getStringExtra(EXTRA_IMAGE));
         }

        ContactDetailViewModel movieDetailViewModel = new ContactDetailViewModel(this,contact);
        movieDetailActivityBinding.setContactDetailViewModel(movieDetailViewModel);
        setTitle(contact.firstName+" "+contact.lastName);
    }

}
