package com.android.gojek.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.gojek.R;
import com.android.gojek.databinding.AddContactBinding;
import com.android.gojek.model.Contact;
import com.android.gojek.viewmodel.AddContactViewModel;

/**
 * Created by dev on 25/04/17.
 */

public class AddContactActivity extends AppCompatActivity {

    private static final String EXTRA_URL = "DETAIL_URL";
    private static final String EXTRA_FIRST_NAME = "CONTACT_FIRST_NAME";
    private static final String EXTRA_LAST_NAME = "CONTACT_LAST_NAME";
    private static final String EXTRA_IMAGE = "IMAGE_URL";

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
            intent.putExtra(EXTRA_IMAGE,contact.contactDetailUrl);
        }

        return intent;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            return true;
        }
        else  if (id == R.id.action_favourite) {
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

        AddContactViewModel addContactViewModel = new AddContactViewModel(this,contact);
        addContactActivityBinding.setAddContactViewModel(addContactViewModel);
        //setTitle(contact.firstName+" "+contact.lastName);
    }

}