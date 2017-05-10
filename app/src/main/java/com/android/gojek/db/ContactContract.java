package com.android.gojek.db;

import android.provider.BaseColumns;

public final class ContactContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ContactContract() {}

    /* Inner class that defines the table contents */
    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "Contact";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_MOBILE = "mobile";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_FAVORITE = "favorite";
        public static final String COLUMN_PROFILE_PIC_URL = "imageUrl";
        public static final String COLUMN_PROFILE_DETAIL_URL = "detailUrl";


    }
}
