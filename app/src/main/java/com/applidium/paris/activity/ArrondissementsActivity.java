package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.NativeGoogleMapFragment;
import com.airbnb.android.airmapview.listeners.OnMapClickListener;
import com.applidium.paris.model.Arrondissement;
import com.applidium.paris.model.ArrondissementProvider;
import com.applidium.paris.util.ColorUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ArrondissementsActivity extends MapListActivity {

    private ArrondissementsAdapter mAdapter;
    private List<Arrondissement>   mArrondissements;
    private AirMapMarker           mSelectionMarker;

    public static Intent makeIntent(Context context) {
        return new Intent(context, ArrondissementsActivity.class);
    }

    @Override
    public ArrondissementsAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ArrondissementsAdapter();
        }
        return mAdapter;
    }

    @Override
    public void onMapReady() {
        super.onMapReady();

        mArrondissements = new ArrondissementProvider().getArrondissements();
        GoogleMap map = ((NativeGoogleMapFragment) mMapFragment.getMapView().getMapInterface()).getGoogleMap();
        for (Arrondissement arrondissement : mArrondissements) {
            try {
                GeoJsonLayer layer = new GeoJsonLayer(map, new JSONObject(arrondissement.getGeoJson()));
                int[] color = ColorUtil.largePalette[arrondissement.getNumber()];
                layer.getDefaultPolygonStyle().setFillColor(Color.argb(0x40, color[0], color[1], color[2]));
                layer.addLayerToMap();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mMapFragment.getMapView().setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                for (Arrondissement arrondissement : mArrondissements) {
                    if (arrondissement.contains(latLng)) {
                        if (mSelectionMarker != null) {
                            if (arrondissement.getName().equals(mSelectionMarker.getTitle())) {
                                return;
                            }
                            mMapFragment.getMapView().getMapInterface().removeMarker(mSelectionMarker);
                        }
                        mSelectionMarker = new AirMapMarker.Builder<>().title(arrondissement.getName()).position(arrondissement.getCenter()).snippet(arrondissement.getOfficialName()).build();
                        mMapFragment.getMapView().addMarker(mSelectionMarker);
                    }
                }
            }
        });
    }

    private class ArrondissementsAdapter extends MapListAdapter<Arrondissement> {
        public ArrondissementsAdapter() {
            super(new ArrondissementProvider().getArrondissements());
        }

        @Override
        public String getTitle(int position) {
            return getItem(position).getName();
        }

        @Override
        public LatLng getLocation(int position) {
            return getItem(position).getCenter();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
