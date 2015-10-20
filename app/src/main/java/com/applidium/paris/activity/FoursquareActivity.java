package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.applidium.paris.model.Venue;
import com.applidium.paris.network.ApiManager;
import com.applidium.paris.network.model.FoursquareResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class FoursquareActivity extends MapListActivity<Venue> {


    public static Intent makeIntent(Context context) {
        return new Intent(context, FoursquareActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshData("48.8567,2.3508");
    }

    private void refreshData(String coordinates) {
        ApiManager.foursquare().search(coordinates, new Callback<FoursquareResponse<Venue.Wrapper>>() {
            @Override
            public void success(FoursquareResponse<Venue.Wrapper> wrapperFoursquareResponse, Response response) {
                setItems(wrapperFoursquareResponse.getResponse().getVenues());
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e(error.getCause(), "Error");
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        refreshData(String.format("%f,%f", location.getLatitude(), location.getLongitude()));
    }
}
