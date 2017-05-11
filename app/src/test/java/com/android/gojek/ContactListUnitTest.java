package com.android.gojek;

import android.content.Context;

import com.android.gojek.db.ContactHelper;
import com.android.gojek.model.Contact;
import com.android.gojek.viewmodel.AddContactViewModel;
import com.android.gojek.viewmodel.ContactListViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactListUnitTest {

    @Mock
    Context mMockContext;

    @Mock
    ContactListViewModel contactListViewModel;


    @Test
    public void testFetchContacts() throws Exception {
        contactListViewModel = mock(ContactListViewModel.class);
        contactListViewModel.fetchContactList();
        verify(contactListViewModel).fetchContactList();
        doCallRealMethod().when(contactListViewModel).fetchContactList();

        AddContactViewModel addContactViewModel = mock(AddContactViewModel.class);
        Contact contact = new Contact();
        contact.setFirstName("Mockito");
        contact.setLastName("Test");
        contact.setPhoneNumber("+910123456789");
        contact.setEmail("Test@yahoo.com");


        doCallRealMethod().when(addContactViewModel).addContact(contact);

        ContactHelper helper = mock(ContactHelper.class);
       ArrayList<Contact> list = doCallRealMethod().when(helper).getAllContacts();

        assertNotNull(list);

    }
}