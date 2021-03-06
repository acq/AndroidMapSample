package com.applidium.map_sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.applidium.map_sample.R;
import com.applidium.map_sample.adapter.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        List<Feature> features = new ArrayList<>();
        features.add(new Feature("Museums", MuseumsActivity.makeIntent(this)));
        features.add(new Feature("Arrondissements", ArrondissementsActivity.makeIntent(this)));
        features.add(new Feature("Sectors", SectorsActivity.makeIntent(this)));
        features.add(new Feature("Foursquare", FoursquareActivity.makeIntent(this)));
        features.add(new Feature("Yelp", YelpActivity.makeIntent(this)));
        features.add(new Feature("Addresses", AddressesActivity.makeIntent(this)));
        features.add(new Feature("Theaters", TheatersActivity.makeIntent(this)));
        features.add(new Feature("Schools", SchoolsActivity.makeIntent(this)));
        features.add(new Feature("Public toilets", PublicToiletsActivity.makeIntent(this)));

        ListView listView = (ListView) findViewById(android.R.id.list);
        FeatureArrayAdapter adapter = new FeatureArrayAdapter(features);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);
    }

    static class Feature {
        String title;
        Intent intent;

        public Feature(String title, Intent intent) {
            this.title = title;
            this.intent = intent;
        }
    }

    private class FeatureArrayAdapter extends ArrayAdapter<Feature> implements AdapterView.OnItemClickListener {
        public FeatureArrayAdapter(List<Feature> features) {
            super(features);
        }

        @Override
        public View getView(int row, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            ((TextView)convertView.findViewById(android.R.id.text1)).setText(getItem(row).title);
            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(getItem(position).intent);
        }
    }
}
