package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.airbnb.android.airmapview.AirMapMarker;
import com.applidium.paris.model.PublicToilet;
import com.applidium.paris.model.PublicToiletProvider;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class PublicToiletsActivity extends MapListActivity {

    private PublicToiletsAdapter mAdapter;

    public static Intent makeIntent(Context context) {
        return new Intent(context, PublicToiletsActivity.class);
    }

    @Override
    public void onMapReady() {
        super.onMapReady();

        List<PublicToilet> toilets = new PublicToiletProvider().getPublicToilets();
        for (PublicToilet toilet : toilets) {
            AirMapMarker<PublicToilet> marker = new AirMapMarker.Builder<PublicToilet>().position(toilet.getPosition()).build();
            mMapFragment.getMapView().addMarker(marker);
        }
    }

    @Override
    public MapListAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new PublicToiletsAdapter();
        }
        return mAdapter;
    }

    private class PublicToiletsAdapter extends MapListAdapter<PublicToilet> {
        public PublicToiletsAdapter() {
            super(new Select().from(PublicToilet.class).queryList());
        }

        @Override
        public String getTitle(int position) {
            return getItem(position).getInfo();
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
