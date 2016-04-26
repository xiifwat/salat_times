package com.gmail.xiifwat.salattimes.database;

import android.provider.BaseColumns;

public class DbContract {

    public static final class LocationEntry implements BaseColumns {

        public static final String TABLE_NAME = "location_info";

        // real
        public static final String COLUMN_LATITUDE = "latitude";
        // real
        public static final String COLUMN_LONGITUDE = "longitude";
        // text
        public static final String COLUMN_CITY = "city";
        // text
        public static final String COLUMN_COUNTRY = "country";
    }
}
