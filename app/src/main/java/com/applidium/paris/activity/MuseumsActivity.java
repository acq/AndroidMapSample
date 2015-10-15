package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;

import com.airbnb.android.airmapview.AirMapMarker;
import com.applidium.paris.model.Museum;
import com.applidium.paris.model.MuseumProvider;

import java.util.List;

public class MuseumsActivity extends MapActivity {
    public static Intent makeIntent(Context context) {
        return new Intent(context, MuseumsActivity.class);
    }

    @Override
    protected void onMapReady() {
        super.onMapReady();
        List<Museum> museums = new MuseumProvider().getMuseums();
        for (Museum museum : museums) {
            mMapView.addMarker(new AirMapMarker.Builder<>().object(museum).title(museum.getName()).position(museum.getCoordinates()).build());
        }
    }
}
