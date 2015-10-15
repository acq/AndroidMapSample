package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class MuseumDetailActivity extends AppCompatActivity {
    private static final String MUSEUM_ID_KEY = "museumId";

    public static Intent makeIntent(Context context, long museumId) {
        Intent intent = new Intent(context, MuseumDetailActivity.class);
        intent.putExtra(MUSEUM_ID_KEY, museumId);
        return intent;
    }


}
