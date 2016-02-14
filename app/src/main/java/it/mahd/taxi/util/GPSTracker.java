package it.mahd.taxi.util;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by salem on 2/13/16.
 */
public class GPSTracker extends Service implements LocationListener {
    private final Context mContext;
    boolean isGPSEnabled = false;// flag for GPS status
    boolean isNetworkEnabled = false;// flag for network status
    boolean canGetLocation = false;// flag for GPS status
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;// The minimum distance to change Updates in meters // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;// The minimum time between updates in milliseconds // 1 minute
    protected LocationManager locationManager;// Declaring a Location Manager

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);// getting GPS status

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);// getting network status

            if (!isGPSEnabled && !isNetworkEnabled) {// no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {// First get location from Network Provider
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled) {// if GPS Enabled get lat/long using GPS Services
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGPS(){//Stop using GPS listener & Calling this function will stop using GPS in your app
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    public double getLatitude(){//Function to get latitude
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){//Function to get longitude
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    //Function to check GPS/wifi enabled
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert(){//Function to show settings alert dialog & On pressing Settings button will lauch Settings Options
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS is settings");// Setting Dialog Title
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");// Setting Dialog Message

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {// On pressing Settings button
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {// on pressing cancel button
                dialog.cancel();
            }
        });

        alertDialog.show();// Showing Alert Message
    }

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
