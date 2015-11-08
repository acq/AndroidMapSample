package com.applidium.map_sample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.applidium.map_sample.model.Theater;
import com.applidium.map_sample.db.TheaterRepository;

public class TheatersActivity extends MapListActivity<Theater> {

    public static Intent makeIntent(Context context) {
        return new Intent(context, TheatersActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItems(new TheaterRepository().getTheaters());
    }

}
