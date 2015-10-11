package com.applidium.paris.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.android.airmapview.AirMapView;
import com.applidium.paris.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AirMapView mapView = (AirMapView) findViewById(R.id.map);
        mapView.initialize(getSupportFragmentManager());
    }

}
