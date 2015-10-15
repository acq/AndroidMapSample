package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;

import com.airbnb.android.airmapview.NativeGoogleMapFragment;
import com.applidium.paris.model.Sector;
import com.applidium.paris.model.SectorProvider;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SectorsActivity extends MapActivity {
    public static Intent makeIntent(Context context) {
        return new Intent(context, SectorsActivity.class);
    }

    @Override
    protected void onMapReady() {
        super.onMapReady();

        List<Sector> sectors = new SectorProvider().getSectors();
        GoogleMap map = ((NativeGoogleMapFragment)mMapView.getMapInterface()).getGoogleMap();
        for (Sector sector : sectors) {
            try {
                GeoJsonLayer layer = new GeoJsonLayer(map, new JSONObject(sector.getGeoJson()));
                layer.getDefaultPolygonStyle().setFillColor(0x203F00FF);
                layer.addLayerToMap();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
