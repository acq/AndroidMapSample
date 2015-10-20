package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.applidium.paris.model.Theater;
import com.applidium.paris.model.TheaterProvider;

public class TheatersActivity extends MapListActivity<Theater> {

    public static Intent makeIntent(Context context) {
        return new Intent(context, TheatersActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItems(new TheaterProvider().getTheaters());
    }

}
