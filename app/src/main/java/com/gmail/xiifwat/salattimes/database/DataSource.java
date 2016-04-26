package com.gmail.xiifwat.salattimes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSource {

    private String TAG = "tfx_" + DataSource.class.getSimpleName();

    private DbHelper dbhelper;
    private SQLiteDatabase database;

    public DataSource(Context context) {

        dbhelper = new DbHelper(context);
    }

    public void open() {
        Log.d(TAG, "Database opened");
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        Log.d(TAG, "Database closed");
        dbhelper.close();
    }

    private List<Integer> cursorToList(Cursor cursor, String columnName) {

        List<Integer> allId = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                allId.add(cursor.getInt(cursor.getColumnIndex(columnName)));
            }
        }
        return allId;
    }

   //   --------- FUNCTION FOR TABLE LOCATION (recently view item)--------

    // ---- delete previous location then && insert new location
    public void insertToLocation(double lat, double lon, String city, String country) {

        database.delete(DbContract.LocationEntry.TABLE_NAME,
                null, null);

        ContentValues values = new ContentValues();
        values.put(DbContract.LocationEntry.COLUMN_LATITUDE, lat);
        values.put(DbContract.LocationEntry.COLUMN_LONGITUDE, lon);
        values.put(DbContract.LocationEntry.COLUMN_CITY, city);
        values.put(DbContract.LocationEntry.COLUMN_COUNTRY, country);

        database.insert(DbContract.LocationEntry.TABLE_NAME, null, values);


    }

    // ---- if there is no location in the table (app launched for the first time)
    // ---- insert default location
    public void insertToLocation() {

        Cursor cursor = database.query(DbContract.LocationEntry.TABLE_NAME,
                null, null, null, null, null, null);

        int rows = cursor.getCount();
        cursor.close();

        if(rows>0) return;

        ContentValues values = new ContentValues();
        values.put(DbContract.LocationEntry.COLUMN_LATITUDE, 22.3667);
        values.put(DbContract.LocationEntry.COLUMN_LONGITUDE, 91.8);
        values.put(DbContract.LocationEntry.COLUMN_CITY, "CHITTAGONG");
        values.put(DbContract.LocationEntry.COLUMN_COUNTRY, "Bangladesh");

        database.insert(DbContract.LocationEntry.TABLE_NAME, null, values);

        Log.d(TAG, "inserted default data");
    }


    public List<String> getLocationName() {

        Cursor cursor =
                database.query(DbContract.LocationEntry.TABLE_NAME,
                        null, null, null, null, null, null);

        cursor.moveToFirst();
        String con = cursor.getString(cursor.getColumnIndex(DbContract.LocationEntry.COLUMN_COUNTRY));
        String city = cursor.getString(cursor.getColumnIndex(DbContract.LocationEntry.COLUMN_CITY));

        cursor.close();

        List<String> retval = new ArrayList<>();
        retval.add(con);
        retval.add(city);

        return retval;
    }

    public List<Double> getLocationCoordinates() {

        Cursor cursor =
                database.query(DbContract.LocationEntry.TABLE_NAME,
                        null, null, null, null, null, null);

        cursor.moveToFirst();
        double lat = cursor.getDouble(cursor.getColumnIndex(DbContract.LocationEntry.COLUMN_LATITUDE));
        double lon = cursor.getDouble(cursor.getColumnIndex(DbContract.LocationEntry.COLUMN_LONGITUDE));

        cursor.close();

        List<Double> retval = new ArrayList<>();
        retval.add(lat);
        retval.add(lon);

        return retval;
    }

}
