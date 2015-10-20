package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.airbnb.android.airmapview.AirMapMarker;
import com.applidium.paris.model.Theater;
import com.applidium.paris.model.Venue;
import com.applidium.paris.network.ApiManager;
import com.applidium.paris.network.model.FoursquareResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class FoursquareActivity extends MapListActivity {


    public static Intent makeIntent(Context context) {
        return new Intent(context, FoursquareActivity.class);
    }

    private List<Venue> mVenues = Collections.emptyList();
    private FoursquareAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiManager.foursquare().search("48.8567,2.3508", new Callback<FoursquareResponse<Venue.Wrapper>>() {
            @Override
            public void success(FoursquareResponse<Venue.Wrapper> wrapperFoursquareResponse, Response response) {
                mVenues = wrapperFoursquareResponse.getResponse().getVenues();
                refreshMarkers();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e(error.getCause(), "Error");
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
        for (Venue venue : mVenues) {
            if (venue.getPosition() != null) {
                AirMapMarker<Theater> marker = new AirMapMarker.Builder<Theater>().position(venue.getPosition()).title(venue.getName()).build();
                mMapFragment.getMapView().addMarker(marker);
            }
        }
    }

    @Override
    public FoursquareAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new FoursquareAdapter();
        }
        return mAdapter;
    }

    private class FoursquareAdapter extends MapListAdapter<Venue> {
        public FoursquareAdapter() {
            super(mVenues);
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
