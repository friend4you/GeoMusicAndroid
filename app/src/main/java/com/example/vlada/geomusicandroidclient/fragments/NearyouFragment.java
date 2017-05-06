package com.example.vlada.geomusicandroidclient.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vlada.geomusicandroidclient.Application;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.api.ServiceGenerator;
import com.example.vlada.geomusicandroidclient.api.model.Record;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NearyouFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private MapView mMapView;
    private GoogleMap googleMap;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private Circle strokeCircle;
    private List<Record> records = new ArrayList<>();

    private final int PERMISSION_REQUEST_CODE = 555;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.nearyou_menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearyou_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                //Initialize Google Play Services
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Location Permission already granted
                        buildGoogleApiClient();
                        googleMap.setMyLocationEnabled(true);
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                    } else {
                        //Request Location Permission
                        checkLocationPermission();
                    }
                } else {
                    buildGoogleApiClient();
                    googleMap.setMyLocationEnabled(true);
                }
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(50.052504, 36.205873));
                markerOptions.title("1");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
                googleMap.addMarker(markerOptions);

                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(50.052938, 36.204038));
                markerOptions.title("2");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
                googleMap.addMarker(markerOptions);

                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(50.050668, 36.206377));
                markerOptions.title("3");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
                googleMap.addMarker(markerOptions);

                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(50.050492, 36.204596));
                markerOptions.title("4");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
                googleMap.addMarker(markerOptions);

                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(50.051150, 36.201426));
                markerOptions.title("5");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
                googleMap.addMarker(markerOptions);

                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(50.051130, 36.204289));
                markerOptions.title("6");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
                googleMap.addMarker(markerOptions);

                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(50.054813, 36.204996));
                markerOptions.title("7");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
                googleMap.addMarker(markerOptions);

                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(50.055451, 36.203562));
                markerOptions.title("8");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
                googleMap.addMarker(markerOptions);

                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(50.054757, 36.206372));
                markerOptions.title("9");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
                googleMap.addMarker(markerOptions);

                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(50.054383, 36.201459));
                markerOptions.title("10");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
                googleMap.addMarker(markerOptions);


            }
        });


        return rootView;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(Application.getSharedInstance().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(Application.getSharedInstance().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //move map camera
        if (strokeCircle != null) {
            strokeCircle.remove();
        }
        strokeCircle = googleMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(100)
                .strokeColor(Color.RED)
                .strokeWidth(2));

        if(outOfRange(latLng, Application.getSharedInstance().getStorage().getRangeLatLng())){
            ServiceGenerator.getGeomusicService().getNearRecords(latLng.latitude, latLng.longitude)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(records -> {
                        drawMarkers(records);
                    }, errors ->{
                        Log.d("getNearRecord", "failure");
                        Toast.makeText(getActivity(), "Failed to get records", Toast.LENGTH_SHORT).show();
                    });
        }

        Log.d("latitude", Double.toString(latLng.latitude));
        Log.d("longitude", Double.toString(latLng.longitude));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().target(latLng).tilt(45).zoom(17).build()));

    }

    public Boolean outOfRange(LatLng current, LatLng start){

        if(current.latitude >= start.latitude + 0.001 || current.latitude <= start.latitude - 0.001){
            return true;
        }
        else{
            if(current.longitude >= start.longitude + 0.001 || current.longitude <= start.longitude - 0.001){
                return true;
            }
            else{
                return false;
            }
        }
    }

    public void drawMarkers(List<Record> records){

        this.records.clear();
        this.records.addAll(records);
        for(Record r : records){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(r.getLat(), r.getLong()));
            markerOptions.title(r.getArtist() + " - " +r.getTitle());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note_black_48dp));
            googleMap.addMarker(markerOptions);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(Application.getSharedInstance().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(Application.getSharedInstance().getApplicationContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(Application.getSharedInstance().getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(Application.getSharedInstance().getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    /*@Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/
}
