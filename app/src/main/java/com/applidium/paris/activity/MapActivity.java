package com.applidium.paris.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.android.airmapview.listeners.OnMapInitializedListener;
import com.applidium.paris.R;
import com.applidium.paris.fragment.MapFragment;

public abstract class MapActivity extends AppCompatActivity {

    protected MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        mMapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mMapFragment.getMapView().setOnMapInitializedListener(new OnMapInitializedListener() {
            @Override
            public void onMapInitialized() {
                onMapReady();
            }
        });
    }

    @CallSuper
    protected void onMapReady() {
        mMapFragment.centerOnParis();
    }
}
