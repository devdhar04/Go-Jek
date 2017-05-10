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

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void initDatBinding()
    {

        activityContactListBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list);
        contactListViewModel = new ContactListViewModel(this);
        activityContactListBinding.setContactListViewModel(contactListViewModel);


    }

    private void setupListContactView(RecyclerView mRecyclerView) {
        ContactListAdapter adapter = new ContactListAdapter();
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //setScrollListener(mRecyclerView,mLayoutManager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        contactListViewModel.reset();
    }



    @Override public void update(Observable observable, Object data) {
        if (observable instanceof ContactListViewModel) {
           ContactListAdapter movieAdapter = (ContactListAdapter) activityContactListBinding.listContact.getAdapter();
            ContactListViewModel movieViewModel = (ContactListViewModel) observable;

           movieAdapter.setContactList(ContactHelper.getInstance(this).getAllContacts());

        }
    }
}
