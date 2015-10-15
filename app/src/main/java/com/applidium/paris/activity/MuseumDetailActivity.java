package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.android.airmapview.AirMapView;
import com.airbnb.android.airmapview.listeners.OnMapInitializedListener;
import com.applidium.paris.R;
import com.applidium.paris.model.Museum;
import com.raizlabs.android.dbflow.sql.language.Select;

public class MuseumDetailActivity extends AppCompatActivity {
    private static final String MUSEUM_ID_KEY = "museumId";

    private Museum     mMuseum;
    private AirMapView mMapView;

    public static Intent makeIntent(Context context, long museumId) {
        Intent intent = new Intent(context, MuseumDetailActivity.class);
        intent.putExtra(MUSEUM_ID_KEY, museumId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_detail);

        mMuseum = new Select().from(Museum.class).byIds(getIntent().getLongExtra(MUSEUM_ID_KEY, 0L)).querySingle();
        setTitle(mMuseum.getName());

        mMapView = (AirMapView) findViewById(R.id.map);
        mMapView.setOnMapInitializedListener(new OnMapInitializedListener() {
            @Override
            public void onMapInitialized() {
                mMapView.setCenterZoom(mMuseum.getCoordinates(), 15);
                mMapView.addMarker(mMuseum.getMarker());
            }
        });
        mMapView.initialize(getSupportFragmentManager());
    }
}
