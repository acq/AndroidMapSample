package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.applidium.paris.R;
import com.applidium.paris.model.Address;
import com.applidium.paris.db.AddressRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressesActivity extends AppCompatActivity {


    public static Intent makeIntent(Context context) {
        return new Intent(context, AddressesActivity.class);
    }

    List<Address>    searchResults = Collections.emptyList();
    AddressesAdapter mAdapter      = new AddressesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        ((EditText) findViewById(android.R.id.edit)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 1) {
                    searchResults = new AddressRepository().search(s.toString());
                } else {
                    searchResults = Collections.emptyList();
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(mAdapter);
    }

    private class AddressesAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        @Override
        public int getCount() {
            return searchResults.size();
        }

        @Override
        public Address getItem(int position) {
            return searchResults.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).getAddress());
            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Address address = getItem(position);
            Map<String, String> details = new HashMap<>();
            details.put("Address", address.getAddress());
            startActivity(DetailActivity.makeIntent(AddressesActivity.this, address.getAddress(), address.getPosition(), details));
        }
    }
}
