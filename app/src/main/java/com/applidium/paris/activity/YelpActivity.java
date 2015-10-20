package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.airbnb.android.airmapview.AirMapMarker;
import com.applidium.paris.model.Business;
import com.applidium.paris.model.Theater;
import com.applidium.paris.network.ApiManager;
import com.applidium.paris.network.model.YelpResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class YelpActivity extends MapListActivity {
    private YelpAdapter mAdapter;
    private List<Business> mBusinesses = Collections.emptyList();

    public static Intent makeIntent(Context context) {
        return new Intent(context, YelpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiManager.yelp().search("Paris", new Callback<YelpResponse>() {
            @Override
            public void success(YelpResponse yelpResponse, Response response) {
                mBusinesses = yelpResponse.getBusinesses();
                refreshMarkers();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e(error.getCause(), "error");
            }
        });
    }

    @Override
    public void onMapReady() {
        super.onMapReady();
        refreshMarkers();
    }

    private void refreshMarkers() {
        mMapFragment.getMapView().clearMarkers();
        for (Business business : mBusinesses) {
            if (business.getPosition() != null) {
                AirMapMarker<Theater> marker = new AirMapMarker.Builder<Theater>().position(business.getPosition()).title(business.getName()).build();
                mMapFragment.getMapView().addMarker(marker);
            }
        }
    }

    @Override
    public MapListAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new YelpAdapter();
        }
        return mAdapter;
    }

    private class YelpAdapter extends MapListAdapter<Business> {
        public YelpAdapter() {
            super(mBusinesses);
        }

        @Override
        public int getCount() {
            return mBusinesses.size();
        }

        @Override
        public Business getItem(int i) {
            return mBusinesses.get(i);
        }

        @Override
        public String getTitle(int position) {
            return getItem(position).getName();
        }

        @Override
        public LatLng getLocation(int position) {
            return getItem(position).getPosition();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
