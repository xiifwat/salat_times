package com.gmail.xiifwat.salattimes.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.gmail.xiifwat.salattimes.R;
import com.gmail.xiifwat.salattimes.database.DataSource;
import com.gmail.xiifwat.salattimes.library.GPSTracker;
import com.gmail.xiifwat.salattimes.library.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private SharedPreferences sharedPref;
    private String LOGTAG = "tfx_" + MainActivity.class.getSimpleName();
    private GPSTracker.MyLocationListener listener;
    private GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listener = new GPSTracker.MyLocationListener() {
            @Override
            public void onLocatinoFound(double latitude, double longitude) {
                Log.d(LOGTAG, "got coordinates from GPS");
                new GetLocationName(latitude, longitude).execute();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPref = getSharedPreferences("SalatTimesSharedPref", Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Utility utility = new Utility(this);

            if(utility.getDataConnectionStatus() && utility.getLocationServiceStatus()) {
                gps = new GPSTracker(MainActivity.this, listener);
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Update Location")
                        .setMessage("Turn on Location and data connection")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetLocationName extends AsyncTask<String,String,String> {

        double latitude, longitude;

        public GetLocationName(double latitude, double longitude) {
//            this.latitude = latitude;
            this.latitude = 74.0059;
//            this.longitude = longitude;
            this.longitude = 40.7128;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO showProgressDialog
        }

        @Override
        protected String doInBackground(String... params) {

            List<String> locName = getLocationName(latitude, longitude);

            Log.d(LOGTAG, "lat:"+latitude+"long:"+longitude+"city:"+locName.get(0)+"con:"+locName.get(1));

            DataSource dataSource = new DataSource(MainActivity.this);
            dataSource.open();
            dataSource.insertToLocation(latitude, longitude, locName.get(0), locName.get(1));
            dataSource.close();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //TODO hideProgressDialog
            gps.disconnect();
        }

        /**
         * This function get location name using Internet
         * from fetched coordinates
         * TODO modification and error checking needed
         **/
        public List<String> getLocationName(double latitude, double longitude) {

            List<String> locationName = new ArrayList<>();

            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);

                if (listAddresses != null && listAddresses.size() > 0) {
                    Address adrs = listAddresses.get(0);

                    locationName.add(adrs.getLocality()); // district name
                    locationName.add(adrs.getCountryName()); // country name
//            	for(int i=0; i<listAddresses.size(); i++)
//            		locationName.append(listAddresses.get(0).getAddressLine(i) + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                locationName.add("Internet access needed to get location name");
            }

            return locationName;
        }

    }
}
