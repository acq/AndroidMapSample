package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.airbnb.android.airmapview.AirMapMarker;
import com.applidium.paris.model.Theater;
import com.applidium.paris.model.TheaterProvider;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class TheatersActivity extends MapListActivity {

    private TheatersAdapter mAdapter;

    public static Intent makeIntent(Context context) {
        return new Intent(context, TheatersActivity.class);
    }

    @Override
    public void onMapReady() {
        super.onMapReady();
        List<Theater> theaters = new TheaterProvider().getTheaters();
        for (Theater theater : theaters) {
            if (theater.getPosition() != null) {
                AirMapMarker<Theater> marker = new AirMapMarker.Builder<Theater>().position(theater.getPosition()).title(theater.getName()).build();
                mMapFragment.getMapView().addMarker(marker);
            }
        }
    }

    @Override
    public TheatersAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new TheatersAdapter();
        }
        return mAdapter;
    }

    private class TheatersAdapter extends MapListAdapter<Theater> {
        public TheatersAdapter() {
            super(new TheaterProvider().getTheaters());
        }

        @Override
        public String getTitle(int position) {
            return getItem(position).getName();
        }

        @Override
        public LatLng getLocation(int position) {
            return getItem(position).getPosition();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
