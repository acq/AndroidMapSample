package com.applidium.map_sample.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.geojson.GeoJsonLayer;
import com.airbnb.android.airmapview.listeners.OnMapClickListener;
import com.applidium.map_sample.model.Sector;
import com.applidium.map_sample.db.SectorRepository;
import com.applidium.map_sample.util.ColorUtil;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class SectorsActivity extends MapListActivity<Sector> {

    private AirMapMarker mSelectionMarker;

    public static Intent makeIntent(Context context) {
        return new Intent(context, SectorsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItems(new SectorRepository().getSectors());
    }

    @Override
    protected void refreshMapContent() {
        if (mMapFragment == null || !mMapFragment.getMapView().isInitialized()) {
            return;
        }

        for (Sector sector : items) {
            try {
                GeoJsonLayer layer = new GeoJsonLayer(mMapFragment.getMapView(), new JSONObject(sector.getGeoJson()));
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
                for (Sector sector : items) {
                    if (sector.contains(latLng)) {
                        if (mSelectionMarker != null) {
                            if (sector.getName().equals(mSelectionMarker.getTitle())) {
                                return;
                            }
                            mMapFragment.getMapView().getMapInterface().removeMarker(mSelectionMarker);
                        }
                        markers.clear();
                        mSelectionMarker = new AirMapMarker.Builder<>().title(sector.getName()).position(sector.getCenter()).build();
                        mMapFragment.getMapView().addMarker(mSelectionMarker);
                        markers.put(sector.getName(), sector);
                    }
                }
            }
        });
    }
}
