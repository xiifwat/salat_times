package com.gmail.xiifwat.salattimes.library;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utility {

    private Context _context;

    public Utility(Context _context) {
        this._context = _context;
    }

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
}
