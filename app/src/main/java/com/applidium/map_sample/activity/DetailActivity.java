package com.applidium.map_sample.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.AirMapView;
import com.airbnb.android.airmapview.listeners.OnMapInitializedListener;
import com.applidium.map_sample.R;
import com.applidium.map_sample.adapter.ArrayAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private static final String TITLE_KEY   = "title";
    private static final String COORDS_KEY = "coords";
    private static final String DETAILS_KEY = "details";

    private static final String EXTRA_CUSTOM_TABS_SESSION       = "android.support.customtabs.extra.SESSION";
    private static final String EXTRA_CUSTOM_TABS_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";

    public static Intent makeIntent(Context context, String title, LatLng coords, Map<String, String> details) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(TITLE_KEY, title);
        intent.putExtra(COORDS_KEY, coords);

        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : details.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        intent.putExtra(DETAILS_KEY, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle(getIntent().getStringExtra(TITLE_KEY));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(android.R.id.list);

        View headerView = getLayoutInflater().inflate(R.layout.map_header, listView, false);
        final AirMapView mapView = (AirMapView) headerView.findViewById(R.id.map);
        mapView.setOnMapInitializedListener(new OnMapInitializedListener() {
            @Override
            public void onMapInitialized() {
                LatLng coords = getIntent().getParcelableExtra(COORDS_KEY);
                mapView.setCenterZoom(coords, 15);
                mapView.addMarker(new AirMapMarker.Builder<>().position(coords).build());
            }
        });
        mapView.initialize(getSupportFragmentManager());
        listView.addHeaderView(headerView);

        DetailAdapter adapter = new DetailAdapter(getIntent().getBundleExtra(DETAILS_KEY));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DetailAdapter extends ArrayAdapter<Detail> implements AdapterView.OnItemClickListener {

        public DetailAdapter(Bundle bundle) {
            super(new ArrayList<Detail>());
            for (String key : bundle.keySet()) {
                elements.add(new Detail(key, bundle.getString(key)));
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            Detail detail = getItem(position);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(detail.title);
            ((TextView) convertView.findViewById(android.R.id.text2)).setText(detail.value);

            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Detail detail = getItem((int) id);
            String value = detail.value;
            if (detail.value.startsWith("www")) {
                value = "http://" + value;
            }
            if (value.startsWith("http")) {
                openUrl(value);
            }
        }
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        Bundle extras = new Bundle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            extras.putBinder(EXTRA_CUSTOM_TABS_SESSION, null);
        }
        extras.putInt(EXTRA_CUSTOM_TABS_TOOLBAR_COLOR, ContextCompat.getColor(DetailActivity.this, R.color.primary));
        intent.putExtras(extras);
        startActivity(intent);
    }

    private class Detail {
        String title;
        String value;

        public Detail(String title, String value) {
            this.title = title;
            this.value = value;
        }
    }
}
