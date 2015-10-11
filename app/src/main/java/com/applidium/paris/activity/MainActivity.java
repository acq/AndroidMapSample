package com.applidium.paris.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.android.airmapview.AirMapView;
import com.airbnb.android.airmapview.listeners.OnMapInitializedListener;
import com.applidium.paris.R;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private AirMapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (AirMapView) findViewById(R.id.map);
        mMapView.setOnMapInitializedListener(new OnMapInitializedListener() {
            @Override
            public void onMapInitialized() {
                mMapView.setCenter(new LatLng(48.8567, 2.3508));
                mMapView.setZoom(12);
            }
        });
        mMapView.initialize(getSupportFragmentManager());
    }

}
