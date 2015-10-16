package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.airbnb.android.airmapview.AirMapMarker;
import com.applidium.paris.model.School;
import com.applidium.paris.model.SchoolProvider;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class SchoolsActivity extends MapListActivity {

    private SchoolsAdapter mAdapter;

    public static Intent makeIntent(Context context) {
        return new Intent(context, SchoolsActivity.class);
    }

    @Override
    public void onMapReady() {
        super.onMapReady();
        List<School> schools = new SchoolProvider().getSchools();
        for (School school : schools) {
            if (school.getPosition() != null) {
                AirMapMarker<School> marker = new AirMapMarker.Builder<School>().position(school.getPosition()).title(school.getName()).build();
                mMapFragment.getMapView().addMarker(marker);
            }
        }
    }

    @Override
    public SchoolsAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new SchoolsAdapter();
        }
        return mAdapter;
    }

    private class SchoolsAdapter extends MapListAdapter<School> {
        public SchoolsAdapter() {
            super(new SchoolProvider().getSchools());
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
