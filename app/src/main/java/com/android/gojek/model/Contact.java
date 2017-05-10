package com.android.gojek.model;

import android.database.Cursor;

import com.android.gojek.db.ContactContract;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dev on 23/04/17.
 */

public class Contact {


    public int id;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("email")
    public String email;
    @SerializedName("favorite")
    public boolean isFavorite;
    @SerializedName("url")
    public String contactDetailUrl;
    @SerializedName("phone_number")
    public String phoneNumber;
    @SerializedName("profile_pic")
    public String contactImageUrl;
    //public String createdDate;
    //public String updatedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getContactDetailUrl() {
        return contactDetailUrl;
    }

    public void setContactDetailUrl(String contactDetailUrl) {
        this.contactDetailUrl = contactDetailUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactImageUrl() {
        return contactImageUrl;
    }

    public void setContactImageUrl(String contactImageUrl) {
        this.contactImageUrl = contactImageUrl;
    }

    public static Contact fromCursor(Cursor cursor) {
        Contact contact = new Contact();
        contact.setFirstName(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_FIRST_NAME)));
        contact.setLastName(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_LAST_NAME)));
        contact.setEmail(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_EMAIL)));
        contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_MOBILE)));
        contact.setId(cursor.getInt(cursor.getColumnIndex(ContactContract.ContactEntry._ID)));
        contact.setFavorite(Boolean.getBoolean(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_FAVORITE))));
        return contact;
    }

}
