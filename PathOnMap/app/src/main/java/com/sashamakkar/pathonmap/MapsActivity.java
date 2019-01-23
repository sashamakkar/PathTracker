package com.sashamakkar.pathonmap;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LatLng lastKnownLocation, myCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        myCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions currentMarker = new MarkerOptions()
                .position(myCurrentLocation);
        mMap.addMarker(currentMarker);

        if(lastKnownLocation == null) {
            lastKnownLocation = myCurrentLocation;
        }
        else {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(myCurrentLocation)
                    .title("My Current Location");
            mMap.addMarker(markerOptions);
            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(lastKnownLocation, myCurrentLocation);
            mMap.addPolyline(polylineOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLocation, 15.0f));
        }
        lastKnownLocation = myCurrentLocation;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
