package com.android.gojek.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.gojek.model.Contact;

import java.util.ArrayList;

/**
 * Created by dev on 09/05/17.
 */

public class ContactHelper {


    private ContactDbHelper mDbHelper;
    private Context context;
    private static ContactHelper singleton = null;
    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private ContactHelper(Context context) {
        mDbHelper = new ContactDbHelper(context);

    }

    /* Static 'instance' method */
    public static ContactHelper getInstance( Context context ) {
         if(singleton == null)
         {
             singleton = new ContactHelper(context);
         }
        return singleton;
    }

    public ArrayList<Contact> getAllContacts()
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


        String[] projection = {
                ContactContract.ContactEntry.COLUMN_ID,
                ContactContract.ContactEntry.COLUMN_FIRST_NAME,
                ContactContract.ContactEntry.COLUMN_LAST_NAME,
                ContactContract.ContactEntry.COLUMN_MOBILE,
                ContactContract.ContactEntry.COLUMN_EMAIL,
                ContactContract.ContactEntry.COLUMN_FAVORITE,
                ContactContract.ContactEntry.COLUMN_PROFILE_DETAIL_URL,
                ContactContract.ContactEntry.COLUMN_PROFILE_PIC_URL
        };


        Cursor cursor = db.query(
                ContactContract.ContactEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        ArrayList<Contact> mArrayList = new ArrayList<Contact>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            Contact contact = new Contact();
            contact.setId(cursor.getInt(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_ID)));
            contact.setFirstName(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_FIRST_NAME)));
            contact.setLastName(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_LAST_NAME)));
            contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_MOBILE)));
            String favorite = cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_FAVORITE));
            contact.setFavorite(favorite.equalsIgnoreCase("true"));
            contact.setEmail(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_EMAIL)));
            contact.setContactDetailUrl(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_PROFILE_DETAIL_URL)));
            contact.setContactImageUrl(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_PROFILE_PIC_URL)));
            mArrayList.add(contact);
        }
    return mArrayList;
    }


public void add(Contact contact)
{
    SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
    ContentValues values = new ContentValues();
    values.put(ContactContract.ContactEntry.COLUMN_FIRST_NAME, contact.firstName);
    values.put(ContactContract.ContactEntry.COLUMN_LAST_NAME, contact.firstName);
    values.put(ContactContract.ContactEntry.COLUMN_MOBILE, contact.phoneNumber);
    values.put(ContactContract.ContactEntry.COLUMN_EMAIL, contact.email);
    values.put(ContactContract.ContactEntry.COLUMN_ID, contact.id);
    values.put(ContactContract.ContactEntry.COLUMN_FAVORITE, String.valueOf(contact.isFavorite));
    values.put(ContactContract.ContactEntry.COLUMN_PROFILE_DETAIL_URL,  contact.contactDetailUrl);
    values.put(ContactContract.ContactEntry.COLUMN_PROFILE_PIC_URL, contact.contactImageUrl);

try {
    long newRowId = db.insert(ContactContract.ContactEntry.TABLE_NAME, null, values);
    Log.v("insert ", "count " + newRowId);

}
catch (SQLiteConstraintException e)
{

}
}




public void update(Contact contact)
{
    SQLiteDatabase db = mDbHelper.getReadableDatabase();

// New value for one column
    ContentValues values = new ContentValues();
    values.put(ContactContract.ContactEntry.COLUMN_FIRST_NAME, contact.firstName);
    values.put(ContactContract.ContactEntry.COLUMN_LAST_NAME, contact.firstName);
    values.put(ContactContract.ContactEntry.COLUMN_MOBILE, contact.phoneNumber);
    values.put(ContactContract.ContactEntry.COLUMN_EMAIL, contact.email);

    int count = db.update(ContactContract.ContactEntry.TABLE_NAME, values, "id = ? ", new String[] { Integer.toString(contact.id) } );
    Log.v("Update ","count "+count);


}
    public void delete(int id)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selection = ContactContract.ContactEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.delete(ContactContract.ContactEntry.TABLE_NAME, selection, selectionArgs);


    }

    protected void onDestroy() {
        mDbHelper.close();

    }

}
