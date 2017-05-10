
package com.android.gojek.data;

import com.android.gojek.model.Contact;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactListResponse {

    @SerializedName("")
    private List<Contact> contactList;

    public List<Contact> getContactList() {
        return contactList;
    }


    public void setContactList(List<Contact> mMovieList) {
        this.contactList = mMovieList;
    }
}
