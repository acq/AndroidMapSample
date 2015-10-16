package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.NativeGoogleMapFragment;
import com.airbnb.android.airmapview.listeners.OnMapClickListener;
import com.applidium.paris.model.Sector;
import com.applidium.paris.model.SectorProvider;
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

    //via http://graphicdesign.stackexchange.com/a/3815
    int[][] colors = new int[][]{{240, 163, 255}, {0, 117, 220}, {153, 63, 0}, {76, 0, 92}, {25, 25, 25}, {0, 92, 49}, {43, 206, 72}, {255, 204, 153}, {128, 128, 128}, {148, 255, 181}, {143, 124, 0}, {157, 204, 0}, {194, 0, 136}, {0, 51, 128}, {255, 164, 5}, {255, 168, 187}, {66, 102, 0}, {255, 0, 16}, {94, 241, 242}, {0, 153, 143}, {224, 255, 102}, {116, 10, 255}, {153, 0, 0}, {255, 255, 128}, {255, 255, 0}, {255, 80, 5}};

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
                int[] color = colors[sector.getArrondissement()];
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
