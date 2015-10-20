package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.applidium.paris.model.Museum;
import com.applidium.paris.model.MuseumProvider;

public class MuseumsActivity extends MapListActivity<Museum> {

    public static Intent makeIntent(Context context) {
        return new Intent(context, MuseumsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItems(new MuseumProvider().getMuseums());
    }

}
