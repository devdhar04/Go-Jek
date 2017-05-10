package com.android.gojek.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactContract.ContactEntry.TABLE_NAME + " (" +
                    ContactContract.ContactEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    ContactContract.ContactEntry.COLUMN_FIRST_NAME + " TEXT," +
                    ContactContract.ContactEntry.COLUMN_LAST_NAME + " TEXT," +
                    ContactContract.ContactEntry.COLUMN_MOBILE + " TEXT," +
                    ContactContract.ContactEntry.COLUMN_EMAIL + " TEXT," +
                    ContactContract.ContactEntry.COLUMN_PROFILE_DETAIL_URL + " TEXT," +
                    ContactContract.ContactEntry.COLUMN_PROFILE_PIC_URL + " TEXT," +
                    ContactContract.ContactEntry.COLUMN_FAVORITE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactContract.ContactEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact.db";

    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}