package com.echcherqaoui.khalid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private String localisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        localisation = getIntent().getStringExtra("localisation");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String latitude = localisation.split(",")[0];
        String longitude = localisation.split(",")[1];
        LatLng location = new LatLng(
                Double.parseDouble(latitude),
                Double.parseDouble(longitude)
        );

        googleMap.addMarker(new MarkerOptions().position(location));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,8));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}