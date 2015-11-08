package com.applidium.map_sample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.applidium.map_sample.model.Museum;
import com.applidium.map_sample.db.MuseumRepository;

public class MuseumsActivity extends MapListActivity<Museum> {

    public static Intent makeIntent(Context context) {
        return new Intent(context, MuseumsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItems(new MuseumRepository().getMuseums());
    }

}
