package com.gmail.xiifwat.salattimes.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gmail.xiifwat.salattimes.R;
import com.gmail.xiifwat.salattimes.database.DataSource;
import com.gmail.xiifwat.salattimes.library.GPSTracker;
import com.gmail.xiifwat.salattimes.library.Georesponse.AddressComponent;
import com.gmail.xiifwat.salattimes.library.Georesponse.GeocodeResponse;
import com.gmail.xiifwat.salattimes.library.Utility;
import com.gmail.xiifwat.salattimes.library.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private String LOGTAG = "tfx_" + MainActivity.class.getSimpleName();
    private GPSTracker.MyLocationListener listener;
    private GPSTracker gps;

    private final String API_KEY = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_frame,new MainActivityFragment(), "tfx")
                .commit();

        listener = new GPSTracker.MyLocationListener() {
            @Override
            public void onLocatinoFound(double latitude, double longitude) {
                Log.d(LOGTAG, "got coordinates from GPS");
                gps.disconnect();
                makeJsonObjReq(latitude, longitude);
            }
        };
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

    //-----------------------------------------------------------
    private void makeJsonObjReq(final double latitude, final double longitude) {

        String url = new Utility().getReverseGeolocationUrl(latitude, longitude, API_KEY);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //						hideProgressDialog();
                        ArrayList<String> address = new Utility().getAddress(response.toString());

                        if(address==null) {
                            // TODO user response
                        } else {
                            Log.d(LOGTAG, address.get(0) + " :: " + address.get(1));

                            // save to database
                            DataSource dataSource = new DataSource(MainActivity.this);
                            dataSource.open();
                            dataSource.insertToLocation(latitude,
                                    longitude, address.get(0), address.get(1));
                            dataSource.close();

                            // update ui
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content_frame,new MainActivityFragment(), "tfx")
                                    .commit();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d(LOGTAG, "Error: " + error.getMessage());
//						hideProgressDialog();
            }
        });

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq,
                "xiifwat");

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
}
