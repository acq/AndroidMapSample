package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.listeners.OnInfoWindowClickListener;
import com.applidium.paris.model.Museum;
import com.applidium.paris.model.MuseumProvider;
import com.google.android.gms.maps.model.Marker;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MuseumsActivity extends MapListActivity {

    private Map<String, Museum> museumMarkers = Collections.emptyMap();

    public static Intent makeIntent(Context context) {
        return new Intent(context, MuseumsActivity.class);
    }

    @Override
    public void onMapReady() {
        super.onMapReady();

        mMapFragment.getMapView().setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(long id) {
                startActivity(MuseumDetailActivity.makeIntent(MuseumsActivity.this, id));
            }

            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(MuseumDetailActivity.makeIntent(MuseumsActivity.this, museumMarkers.get(marker.getTitle()).getId()));
            }
        });

        List<Museum> museums = new MuseumProvider().getMuseums();
        museumMarkers = new HashMap<>(museums.size());
        for (Museum museum : museums) {
            AirMapMarker<Museum> marker = museum.getMarker();
            museumMarkers.put(marker.getTitle(), museum);

            mMapFragment.getMapView().addMarker(marker);
        }
    }
}
