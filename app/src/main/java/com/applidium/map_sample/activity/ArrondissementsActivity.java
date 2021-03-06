package com.applidium.map_sample.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.geojson.GeoJsonLayer;
import com.airbnb.android.airmapview.listeners.OnMapClickListener;
import com.applidium.map_sample.model.Arrondissement;
import com.applidium.map_sample.db.ArrondissementRepository;
import com.applidium.map_sample.util.ColorUtil;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class ArrondissementsActivity extends MapListActivity<Arrondissement> {

    private AirMapMarker           mSelectionMarker;

    public static Intent makeIntent(Context context) {
        return new Intent(context, ArrondissementsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItems(new ArrondissementRepository().getArrondissements());
    }

    @Override
    protected void refreshMapContent() {
        if (mMapFragment == null || !mMapFragment.getMapView().isInitialized()) {
            return;
        }

        for (Arrondissement arrondissement : items) {
            try {
                GeoJsonLayer layer = new GeoJsonLayer(mMapFragment.getMapView(), new JSONObject(arrondissement.getGeoJson()));
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
                for (Arrondissement arrondissement : items) {
                    if (arrondissement.contains(latLng)) {
                        if (mSelectionMarker != null) {
                            if (arrondissement.getName().equals(mSelectionMarker.getTitle())) {
                                return;
                            }
                            mMapFragment.getMapView().getMapInterface().removeMarker(mSelectionMarker);
                        }
                        markers.clear();
                        mSelectionMarker = new AirMapMarker.Builder<>().title(arrondissement.getName()).position(arrondissement.getCenter()).snippet(arrondissement.getOfficialName()).build();
                        mMapFragment.getMapView().addMarker(mSelectionMarker);
                        markers.put(arrondissement.getName(), arrondissement);
                    }
                }
            }
        });
    }
}
