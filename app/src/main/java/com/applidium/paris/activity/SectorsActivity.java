package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.NativeGoogleMapFragment;
import com.airbnb.android.airmapview.listeners.OnMapClickListener;
import com.applidium.paris.model.Sector;
import com.applidium.paris.model.SectorProvider;
import com.applidium.paris.util.ColorUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SectorsActivity extends MapListActivity {

    private List<Sector> mSectors;
    private AirMapMarker mSelectionMarker;
    private SectorsAdapter mAdapter;

    public static Intent makeIntent(Context context) {
        return new Intent(context, SectorsActivity.class);
    }

    @Override
    public void onMapReady() {
        super.onMapReady();

        mSectors = new SectorProvider().getSectors();
        GoogleMap map = ((NativeGoogleMapFragment) mMapFragment.getMapView().getMapInterface()).getGoogleMap();
        for (Sector sector : mSectors) {
            try {
                GeoJsonLayer layer = new GeoJsonLayer(map, new JSONObject(sector.getGeoJson()));
                int[] color = ColorUtil.largePalette[sector.getArrondissement()];
                layer.getDefaultPolygonStyle().setFillColor(Color.argb(0x40, color[0], color[1], color[2]));
                layer.addLayerToMap();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mMapFragment.getMapView().setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                for (Sector sector : mSectors) {
                    if (sector.contains(latLng)) {
                        if (mSelectionMarker != null) {
                            if (sector.getName().equals(mSelectionMarker.getTitle())) {
                                return;
                            }
                            mMapFragment.getMapView().getMapInterface().removeMarker(mSelectionMarker);
                        }
                        mSelectionMarker = new AirMapMarker.Builder<>().title(sector.getName()).position(sector.getCenter()).build();
                        mMapFragment.getMapView().addMarker(mSelectionMarker);
                    }
                }
            }
        });
    }

    @Override
    public SectorsAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new SectorsAdapter();
        }
        return mAdapter;
    }

    private class SectorsAdapter extends MapListAdapter<Sector> {
        public SectorsAdapter() {
            super(new SectorProvider().getSectors());
        }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public String getTitle(int position) {
            return getItem(position).getName();
        }

        @Override
        public LatLng getLocation(int position) {
            return getItem(position).getCenter();
        }
    }
}
