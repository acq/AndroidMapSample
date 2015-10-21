package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.applidium.paris.adapter.ArrayAdapter;
import com.applidium.paris.model.Event;
import com.applidium.paris.model.EventProvider;

public class EventsActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, EventsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.list_content);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new EventsAdapter());
    }

    private class EventsAdapter extends ArrayAdapter<Event> {
        public EventsAdapter() {
            super(new EventProvider().getEvents());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).getTitle());
            ((TextView) convertView.findViewById(android.R.id.text2)).setText(getItem(position).getSpace_time_info());
            return convertView;
        }
    }
}
