package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.listeners.OnInfoWindowClickListener;
import com.applidium.paris.model.Museum;
import com.applidium.paris.model.MuseumProvider;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MuseumsActivity extends MapListActivity {

    private Map<String, Museum> museumMarkers = Collections.emptyMap();
    private MuseumsAdapter mAdapter;

    public static Intent makeIntent(Context context) {
        return new Intent(context, MuseumsActivity.class);
    }

    @Override
    public void onMapReady() {
        super.onMapReady();

        mMapFragment.getMapView().setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(long id) {
                // TODO
            }

            @Override
            public void onInfoWindowClick(Marker marker) {
                showDetail(museumMarkers.get(marker.getTitle()));
            }
        });

        List<Museum> museums = new MuseumProvider().getMuseums();
        museumMarkers = new HashMap<>(museums.size());
        for (Museum museum : museums) {
            if (museum.getCoordinates() != null) {
                AirMapMarker<Museum> marker = museum.getMarker();
                museumMarkers.put(marker.getTitle(), museum);
                mMapFragment.getMapView().addMarker(marker);
            }
        }
    }

    @Override
    public MuseumsAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new MuseumsAdapter();
        }
        return mAdapter;
    }

    private class MuseumsAdapter extends MapListAdapter<Museum> {
        public MuseumsAdapter() {
            super(new MuseumProvider().getMuseums());
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showDetail(getItem(position));
        }

        @Override
        public String getTitle(int position) {
            return getItem(position).getName();
        }

        @Override
        public LatLng getLocation(int position) {
            return getItem(position).getCoordinates();
        }
    }

    public void showDetail(Museum museum) {
        startActivity(DetailActivity.makeIntent(this, museum.getName(), museum.getCoordinates(), museum.getDetailsBundle()));
    }
}
