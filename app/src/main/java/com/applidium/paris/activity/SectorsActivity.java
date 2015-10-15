package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.airbnb.android.airmapview.NativeGoogleMapFragment;
import com.applidium.paris.model.Sector;
import com.applidium.paris.model.SectorProvider;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SectorsActivity extends MapListActivity {
    public static Intent makeIntent(Context context) {
        return new Intent(context, SectorsActivity.class);
    }

    @Override
    public void onMapReady() {
        super.onMapReady();

        List<Sector> sectors = new SectorProvider().getSectors();
        GoogleMap map = ((NativeGoogleMapFragment) mMapFragment.getMapView().getMapInterface()).getGoogleMap();
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

    @Override
    public SectorsAdapter getAdapter() {
        return new SectorsAdapter();
    }

    private class SectorsAdapter extends MapListAdapter<Sector> {
        public SectorsAdapter() {
            super(new SectorProvider().getSectors());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).getName());
            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
