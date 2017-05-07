package com.android.gojek.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.gojek.R;
import com.android.gojek.databinding.AddContactBinding;
import com.android.gojek.model.Contact;
import com.android.gojek.viewmodel.AddContactViewModel;

import java.io.IOException;

/**
 * Created by dev on 25/04/17.
 */

public class AddContactActivity extends AppCompatActivity {

    private static final String EXTRA_URL = "DETAIL_URL";
    private static final String EXTRA_FIRST_NAME = "CONTACT_FIRST_NAME";
    private static final String EXTRA_LAST_NAME = "CONTACT_LAST_NAME";
    private static final String EXTRA_IMAGE = "IMAGE_URL";
    private static final String EXTRA_MOBILE = "CONTACT_MOBILE";
    private static final String EXTRA_EMAIL = "CONTACT_EMAIL";
    private static final String EXTRA_SCREEN_NAME = "CONTACT_ADD";
    private static final String EXTRA_CONTACT_ID = "CONTACT_ID";




    private AddContactBinding addContactActivityBinding;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContactActivityBinding =
                DataBindingUtil.setContentView(this, R.layout.add_contact);
       // setSupportActionBar(addContactActivityBinding.toolbar);
        getExtrasFromIntent();
    }

    public static Intent launchDetail(Context context, Contact contact) {
        Intent intent = new Intent(context, AddContactActivity.class);

        if(contact != null)
        {
            intent.putExtra(EXTRA_URL,contact.contactDetailUrl);
            intent.putExtra(EXTRA_FIRST_NAME,contact.firstName);
            intent.putExtra(EXTRA_LAST_NAME,contact.lastName);
            intent.putExtra(EXTRA_IMAGE,contact.contactImageUrl);
            intent.putExtra(EXTRA_MOBILE,contact.phoneNumber);
            intent.putExtra(EXTRA_EMAIL,contact.email);

            intent.putExtra(EXTRA_CONTACT_ID,contact.id);

        }
        else
        {
            intent.putExtra(EXTRA_SCREEN_NAME,EXTRA_SCREEN_NAME);
        }

        return intent;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

            getMenuInflater().inflate(R.menu.menu_add_contact, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            Contact contact = new Contact();
            contact.setFirstName(addContactActivityBinding.nameField.getText().toString());
            contact.setLastName(addContactActivityBinding.lastNameField.getText().toString());
            contact.setEmail(addContactActivityBinding.emailField.getText().toString());
            contact.setPhoneNumber(addContactActivityBinding.phoneField.getText().toString());

            if(getIntent().getStringExtra(EXTRA_SCREEN_NAME) != null && getIntent().getStringExtra(EXTRA_SCREEN_NAME).equals(EXTRA_SCREEN_NAME)) {

                addContactActivityBinding.getAddContactViewModel().addContact(contact);
            }
            else {
                addContactActivityBinding.getAddContactViewModel().updateContact(contact);
            }

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
            contact.setPhoneNumber(getIntent().getStringExtra(EXTRA_MOBILE));
            contact.setEmail(getIntent().getStringExtra(EXTRA_EMAIL));
            contact.setId(getIntent().getIntExtra(EXTRA_CONTACT_ID,0));
        }

        AddContactViewModel addContactViewModel = new AddContactViewModel(this,contact);
        addContactActivityBinding.setAddContactViewModel(addContactViewModel);
        //setTitle(contact.firstName+" "+contact.lastName);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddContactViewModel.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                addContactActivityBinding.contactImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}