package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.listeners.OnInfoWindowClickListener;
import com.applidium.paris.model.Museum;
import com.applidium.paris.model.MuseumProvider;
import com.google.android.gms.maps.model.Marker;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MuseumsActivity extends MapActivity {

    private Map<String, Museum> museumMarkers = Collections.emptyMap();

    public static Intent makeIntent(Context context) {
        return new Intent(context, MuseumsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(long id) {
                startActivity(MuseumDetailActivity.makeIntent(MuseumsActivity.this, id));
            }

            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(MuseumDetailActivity.makeIntent(MuseumsActivity.this, museumMarkers.get(marker.getTitle()).getId()));
            }
        });
    }

    @Override
    protected void onMapReady() {
        super.onMapReady();
        List<Museum> museums = new MuseumProvider().getMuseums();
        museumMarkers = new HashMap<>(museums.size());
        for (Museum museum : museums) {
            AirMapMarker.Builder<Museum> builder = new AirMapMarker.Builder<Museum>();
            builder.object(museum);
            builder.title(museum.getName());
            builder.position(museum.getCoordinates());
            builder.id(museum.getId());
            AirMapMarker<Museum> marker = builder.build();

            museumMarkers.put(marker.getTitle(), museum);

            mMapView.addMarker(marker);
        }
    }
}
