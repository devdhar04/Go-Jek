package com.android.gojek.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.gojek.R;
import com.android.gojek.databinding.ActivityContactListBinding;
import com.android.gojek.db.ContactHelper;
import com.android.gojek.view.adapter.ContactListAdapter;
import com.android.gojek.viewmodel.ContactListViewModel;

import java.util.Observable;
import java.util.Observer;

public class ContactListActivity extends AppCompatActivity implements Observer {


    public ActivityContactListBinding activityContactListBinding;
    private ContactListViewModel contactListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatBinding();
        setupListContactView(activityContactListBinding.listContact);

        setSupportActionBar(activityContactListBinding.toolbar);
        setupObserver(contactListViewModel);
        ContactHelper.getInstance(this);

    }

    private void initDatBinding() {

        activityContactListBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list);
        contactListViewModel = new ContactListViewModel(this);
        activityContactListBinding.setContactListViewModel(contactListViewModel);


    }

    private void setupListContactView(RecyclerView mRecyclerView) {
        ContactListAdapter adapter = new ContactListAdapter();
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }


    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactListViewModel.reset();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ContactListAdapter contactAdapter = (ContactListAdapter) activityContactListBinding.listContact.getAdapter();
        contactAdapter.setContactList(ContactHelper.getInstance(this).getAllContacts());
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof ContactListViewModel) {
            ContactListAdapter contactAdapter = (ContactListAdapter) activityContactListBinding.listContact.getAdapter();
            contactAdapter.setContactList(ContactHelper.getInstance(this).getAllContacts());

        }
    }
}
