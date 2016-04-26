package com.gmail.xiifwat.salattimes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "salatTimes.db";

    private static final int DB_VERSION = 1;


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_LOCATION_TABLE =
                "CREATE TABLE " + DbContract.LocationEntry.TABLE_NAME + " (" +
                        DbContract.LocationEntry.COLUMN_LATITUDE + " REAL NOT NULL, " +
                        DbContract.LocationEntry.COLUMN_LONGITUDE + " REAL NOT NULL, " +
                        DbContract.LocationEntry.COLUMN_COUNTRY + " TEXT NOT NULL, " +
                        DbContract.LocationEntry.COLUMN_CITY + " TEXT NOT NULL " +
                        " );";

        db.execSQL(CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DbContract.LocationEntry.TABLE_NAME);
        onCreate(db);
    }
}
