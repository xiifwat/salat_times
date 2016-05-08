package com.gmail.xiifwat.salattimes.library;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GPSTracker
		implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, LocationSource.OnLocationChangedListener,
		LocationListener {

	private String LOGTAG = "tfx_" + GPSTracker.class.getSimpleName();
	private GoogleApiClient mApiClient;
	private LocationRequest mLocationRequest;

	private MyLocationListener listener;
	private Context context;
//	private boolean gotLocation = false;


	public GPSTracker(Context context, MyLocationListener listener) {

		mApiClient = new GoogleApiClient.Builder(context)
				.addApi(LocationServices.API)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.build();

		this.context = context;
		this.listener = listener;

		if(!mApiClient.isConnected()) mApiClient.connect();

		Log.d(LOGTAG, "trying to get location");
	}

	public void disconnect() {
		if(mApiClient.isConnected()) mApiClient.disconnect();
	}

	@Override
	public void onConnected(Bundle bundle) {

		mLocationRequest = new LocationRequest()
				.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
				.setInterval(60 * 1000);

		LocationServices.FusedLocationApi.requestLocationUpdates(
				mApiClient, mLocationRequest, this);
	}

	@Override
	public void onConnectionSuspended(int i) {
//
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

	}

	@Override
	public void onLocationChanged(Location location) {
		listener.onLocatinoFound(location.getLatitude(), location.getLongitude());
	}

	public interface MyLocationListener {
		void onLocatinoFound(double latitude, double longitude);
	}
}
