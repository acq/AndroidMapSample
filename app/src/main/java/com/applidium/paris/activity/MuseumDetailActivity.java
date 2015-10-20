package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.android.airmapview.AirMapView;
import com.airbnb.android.airmapview.listeners.OnMapInitializedListener;
import com.applidium.paris.R;
import com.applidium.paris.adapter.ArrayAdapter;
import com.applidium.paris.model.Museum;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;

public class MuseumDetailActivity extends AppCompatActivity {
    private static final String MUSEUM_ID_KEY = "museumId";

    private static final String EXTRA_CUSTOM_TABS_SESSION       = "android.support.customtabs.extra.SESSION";
    private static final String EXTRA_CUSTOM_TABS_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";

    private Museum mMuseum;

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

        listView.setAdapter(new MuseumDetailAdapter(mMuseum));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + mMuseum.getWebsite()));
                Bundle extras = new Bundle();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    extras.putBinder(EXTRA_CUSTOM_TABS_SESSION, null);
                }
                extras.putInt(EXTRA_CUSTOM_TABS_TOOLBAR_COLOR, ContextCompat.getColor(MuseumDetailActivity.this, R.color.primary));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private class MuseumDetailAdapter extends ArrayAdapter<Detail> {

        public MuseumDetailAdapter(Museum museum) {
            super(new ArrayList<Detail>());
            elements.add(new Detail("Name", museum.getName()));
            elements.add(new Detail("Address", String.format("%s, %s %s", museum.getAddress(), museum.getPostalCode(), museum.getCity())));
            elements.add(new Detail("Opening hours", museum.getOpeningHours()));
            elements.add(new Detail("Closed days", museum.getClosedDays()));
            elements.add(new Detail("Web site", museum.getWebsite()));
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
