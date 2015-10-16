package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.airbnb.android.airmapview.NativeGoogleMapFragment;
import com.applidium.paris.model.PublicToilet;
import com.applidium.paris.model.PublicToiletProvider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
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
        List<LatLng> coordinates = new ArrayList<>(toilets.size());
        for (PublicToilet toilet : toilets) {
            coordinates.add(toilet.getPosition());
            //AirMapMarker<PublicToilet> marker = new AirMapMarker.Builder<PublicToilet>().position(toilet.getPosition()).build();
            //mMapFragment.getMapView().addMarker(marker);
        }
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder().data(coordinates).build();
        GoogleMap map = ((NativeGoogleMapFragment) mMapFragment.getMapView().getMapInterface()).getGoogleMap();
        map.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
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
            super(new PublicToiletProvider().getPublicToilets());
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
