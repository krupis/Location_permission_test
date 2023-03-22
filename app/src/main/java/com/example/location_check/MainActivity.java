package com.example.location_check;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.location.*;






public class MainActivity extends AppCompatActivity implements LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    String provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);





        //Requesting permissions info taken from:
        //https://developer.android.com/training/permissions/requesting
        //https://developer.android.com/training/location/permissions
        //STEPS:
        /*
        1. check if location permission is granted using ContextCompat.checkSelfPermission() method this should return PERMISSION_GRANTED or PERMISSION_DENIED
        2. Request permissions with requestPermissions()
        3. If allowing system to manage the permission request code, add dependancies on the following libraries in build.gradle file:
            androidx.activity, version 1.2.0 or later
            androidx.fragment, version 1.3.0 or later

         */

        //REQUESTING FOR PERMISSION:
        checkLocationPermission();







    }//end of onCreate


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "onResume method called");
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause", "onPause method called");
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.removeUpdates(this);
        }
    }



    @Override
    public void onLocationChanged(Location location) {

        Double lat = location.getLatitude();
        Double lng = location.getLongitude();

        Log.i("Location info: Lat", lat.toString());
        Log.i("Location info: Lng", lng.toString());

    }



    private boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("CHECK", "ACCESS_FINE_LOCATION IS NOT GRANTED");

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.e("CHECK", "SHOWING RATIONALE");

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location access permission")
                        .setMessage("This app requires location access permission")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                Log.e("CHECK", "REQUESTING FINE and COARSE LOCATION");
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                            }
                        })
                        .create()
                        .show();


            } else {
                Log.e("CHECK", "REQUESTING FINE and COARSE LOCATION");
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
            return false;
        }
        else {
            Log.e("CHECK", "ACCESS_FINE_LOCATION AND ACCESS_COARSE_LOCATION IS ALREADY GRANTED");
            return true;
        }
    }







    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("onRequestPermissionsResult", "PERMISSION GRANTED");
                    // permission was granted, yay! Do the
                    // location-related task you need to do.

                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }



                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }









































    /*
    // MY INITIAL CHECK LOCATION PERMISSION

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // if fine location is not granted, request for permission.
            Log.e("CHECK", "REQUEST LOCATION PERMISSION");
            requestLocationPermission();
        }
        else {
            // if fine location is granted, request for background permission
            Log.e("CHECK", "CHECK BACKGROUND LOCATION");
            checkBackgroundLocation();
        }
    }


    private void checkBackgroundLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("CHECK", "REQUEST BACKGROUND PERMISSION");
            requestBackgroundLocationPermission();
        }
        else{
            Log.e("CHECK", "BACKGROUND PERMISSION ALREADY GRANTED");
        }
    }




    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
    }

    private void requestBackgroundLocationPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 100);
    }

     */









}// end of MainActivity


