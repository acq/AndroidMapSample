package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.airbnb.android.airmapview.AirMapView;
import com.airbnb.android.airmapview.listeners.OnMapInitializedListener;
import com.applidium.paris.R;
import com.applidium.paris.model.Museum;
import com.raizlabs.android.dbflow.sql.language.Select;

public class MuseumDetailActivity extends AppCompatActivity {
    private static final String MUSEUM_ID_KEY = "museumId";

    private Museum     mMuseum;

    public static Intent makeIntent(Context context, long museumId) {
        Intent intent = new Intent(context, MuseumDetailActivity.class);
        intent.putExtra(MUSEUM_ID_KEY, museumId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_detail);

        mMuseum = new Select().from(Museum.class).byIds(getIntent().getLongExtra(MUSEUM_ID_KEY, 0L)).querySingle();
        setTitle(mMuseum.getName());

        ListView listView = (ListView) findViewById(android.R.id.list);

        View headerView = getLayoutInflater().inflate(R.layout.map_header, listView, false);
        final AirMapView mapView = (AirMapView) headerView.findViewById(R.id.map);
        mapView.setOnMapInitializedListener(new OnMapInitializedListener() {
            @Override
            public void onMapInitialized() {
                mapView.setCenterZoom(mMuseum.getCoordinates(), 15);
                mapView.addMarker(mMuseum.getMarker());
            }
        });
        mapView.initialize(getSupportFragmentManager());
        listView.addHeaderView(headerView);

        listView.setAdapter(new MuseumDetailAdapter());
    }

    private class MuseumDetailAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
