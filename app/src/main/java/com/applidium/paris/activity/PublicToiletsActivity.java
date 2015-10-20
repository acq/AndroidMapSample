package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.android.airmapview.NativeGoogleMapFragment;
import com.applidium.paris.model.PublicToilet;
import com.applidium.paris.model.PublicToiletProvider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.List;

public class PublicToiletsActivity extends MapListActivity<PublicToilet> {

    public static Intent makeIntent(Context context) {
        return new Intent(context, PublicToiletsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItems(new PublicToiletProvider().getPublicToilets());
    }

    @Override
    protected void refreshMapContent() {
        if (mMapFragment == null || !mMapFragment.getMapView().isInitialized()) {
            return;
        }

        List<LatLng> coordinates = new ArrayList<>(items.size());
        for (PublicToilet toilet : items) {
            coordinates.add(toilet.getPosition());
            //AirMapMarker<PublicToilet> marker = new AirMapMarker.Builder<PublicToilet>().position(toilet.getPosition()).build();
            //mMapFragment.getMapView().addMarker(marker);
        }
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder().data(coordinates).build();
        GoogleMap map = ((NativeGoogleMapFragment) mMapFragment.getMapView().getMapInterface()).getGoogleMap();
        map.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
    }
}
