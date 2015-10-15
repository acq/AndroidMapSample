package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;

public class MuseumsActivity extends MapActivity {
    public static Intent makeIntent(Context context) {
        return new Intent(context, MuseumsActivity.class);
    }
}
