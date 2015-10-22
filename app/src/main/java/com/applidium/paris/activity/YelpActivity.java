package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.applidium.paris.model.Business;
import com.applidium.paris.network.ApiManager;
import com.applidium.paris.network.model.YelpResponse;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Select;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class YelpActivity extends MapListActivity<Business> {
    public static Intent makeIntent(Context context) {
        return new Intent(context, YelpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiManager.yelp().search("Paris", new Callback<YelpResponse>() {
            @Override
            public void success(YelpResponse yelpResponse, Response response) {
                TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(yelpResponse.getBusinesses())));
                setItems(new Select().from(Business.class).queryList());
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e(error.getCause(), "error");
                setItems(new Select().from(Business.class).queryList());
            }
        });
    }
}
