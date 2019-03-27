package com.acme.eac3ccl;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    MapView mapView;
    View mView;
    final int REQUESTCODE = 1234;


    public Mapa() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //Per afegir el menu al fragment
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragmentmapa, container, false);
        return mView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) mView.findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(final GoogleMap gMap) {
        MapsInitializer.initialize(getContext());
        googleMap = gMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            String[] permisos = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permisos, REQUESTCODE);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        gMap.setMyLocationEnabled(true);
        LocationManager locationManager =  getActivity().getSystemService(LocationManager.class);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location != null){
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Toast.makeText(getContext(), "" + latitude +" , " + longitude + "",Toast.LENGTH_SHORT);
        }

        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setCompassEnabled(true);
        gMap.getUiSettings().setMapToolbarEnabled(true);
        gMap.getUiSettings().setAllGesturesEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setTrafficEnabled(true);

        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();
                // Setting the position for the marker
                markerOptions.position(latLng);
                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                // Clears the previously touched position
                gMap.clear();
                // Animating to the touched position
                gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                // Placing a marker on the touched position
                gMap.addMarker(markerOptions);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu mimenu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menumapa, mimenu);

        //getMenuInflater instancia menusInflater.
        //cOn esto hemos creado el menú

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.SubMnuOpc1:
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.SubMnuOpc2:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.SubMnuOpc3:
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.SubMnuOpc4:
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }

        return true;

    }
}



/**
if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
        if(!gMap.isMyLocationEnabled())
        gMap.setMyLocationEnabled(true);

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (myLocation == null) {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        String provider = lm.getBestProvider(criteria, true);
        myLocation = lm.getLastKnownLocation(provider);
        }

        if(myLocation!=null){
        LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
        }
        }

        **/