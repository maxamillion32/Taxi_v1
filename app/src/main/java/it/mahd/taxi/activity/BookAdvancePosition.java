package it.mahd.taxi.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import it.mahd.taxi.Main;
import it.mahd.taxi.R;
import it.mahd.taxi.util.Controllers;
import it.mahd.taxi.util.GPSTracker;

/**
 * Created by salem on 3/2/16.
 */
public class BookAdvancePosition extends Fragment {
    Controllers conf = new Controllers();
    GPSTracker gps;
    MapView mMapView;
    private GoogleMap googleMap;
    double latitude, longitude;

    public BookAdvancePosition() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.booknow, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //mMapView.setBuiltInZoomControls(true);
        googleMap = mMapView.getMap();
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMyLocationEnabled(true);
        /*googleMap.setOnMarkerDragListener(this);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMapClickListener(this);*/
        gps = new GPSTracker(BookAdvancePosition.this.getActivity());
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            gps.showSettingsAlert();
            latitude = 0;
            longitude = 0;
        }
        /*MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");// create marker
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));// Changing marker icon
        googleMap.addMarker(marker);// adding marker*/
        MarkerOptions x = new MarkerOptions().position(new LatLng(35.005, 10.005)).title("Hello Maps").snippet("First Marker");
        x.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        googleMap.addMarker(x);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(35.005, 10.005)).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker arg0) {
                arg0.remove();
                Toast.makeText(getActivity()
                        , "Remove Marker " + String.valueOf(arg0.getId())
                        , Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        googleMap.setOnMapClickListener(new OnMapClickListener() {
            public void onMapClick(LatLng arg0) {
                googleMap.clear();
                latitude = arg0.latitude;
                longitude = arg0.longitude;
                googleMap.addMarker(new MarkerOptions().position(arg0)
                        .title("Hello Maps").snippet("First Marker")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        Fragment fr = new BookAdvance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putDouble(conf.tag_latitude, latitude);
        args.putDouble(conf.tag_longitude, longitude);
        fr.setArguments(args);
        ft.replace(R.id.container_body, fr);
        ft.addToBackStack(null);
        ft.commit();
        ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.advance));
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    /*@Override
    public void onMarkerDrag(Marker arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMarkerDragEnd(Marker arg0) {
        // TODO Auto-generated method stub
        LatLng dragPosition = arg0.getPosition();
        double dragLat = dragPosition.latitude;
        double dragLong = dragPosition.longitude;
        //Log.i("info", "on drag end :" + dragLat + " dragLong :" + dragLong);
        Toast.makeText(getActivity(), "Marker Dragged..!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMarkerDragStart(Marker arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMapClick(LatLng arg0) {
        // TODO Auto-generated method stub
        googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));
        MarkerOptions x = new MarkerOptions().position(new LatLng(arg0.latitude, arg0.longitude)).title("Hello Maps").snippet("First Marker");
        x.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        googleMap.addMarker(x);
    }


    @Override
    public void onMapLongClick(LatLng arg0) {
        // TODO Auto-generated method stub

        //create new marker when user long clicks
        googleMap.addMarker(new MarkerOptions()
                .position(arg0)
                .draggable(true));
    }*/
}
