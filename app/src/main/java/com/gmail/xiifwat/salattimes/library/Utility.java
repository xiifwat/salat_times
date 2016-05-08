package com.gmail.xiifwat.salattimes.library;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.gmail.xiifwat.salattimes.library.Georesponse.AddressComponent;
import com.gmail.xiifwat.salattimes.library.Georesponse.GeocodeResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;

public class Utility {

    private Context _context;

    public Utility(Context _context) {
        this._context = _context;
    }

    public Utility(){}

    public boolean getDataConnectionStatus() {

        ConnectivityManager connec = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net_info = connec.getActiveNetworkInfo();
        boolean statusOfInternet = net_info != null && net_info.isConnected();

        return statusOfInternet;
    }

    public boolean getLocationServiceStatus() {

        LocationManager manager = (LocationManager) _context.getSystemService(_context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return statusOfGPS;
    }

    public String getReverseGeolocationUrl(double latitude, double longitude, String apiKey) {

        return "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
                String.valueOf(latitude) + "," + String.valueOf(longitude) +
                "&key=" + apiKey;
    }

    public ArrayList<String> getAddress(String jsonResponse) {

        ArrayList<String> address = new ArrayList<>();

        GeocodeResponse geocodeResponse =
                new Gson().fromJson(jsonResponse, GeocodeResponse.class);

        if(!geocodeResponse.getStatus().equalsIgnoreCase("ok")) {
            // problem getting response from google API
            return null;
        }

        Collection<AddressComponent> x =
                geocodeResponse.getResults().get(0).getAddress_components();
        for (AddressComponent ac : x) {

            for(String s : ac.getTypes()) {
                if(s.equalsIgnoreCase("country")) {
                    address.add(ac.getLong_name());
                } else if(s.equalsIgnoreCase("administrative_area_level_2")) {
                    address.add(ac.getLong_name());
                }
            }
        }

        return address;
    }
}
