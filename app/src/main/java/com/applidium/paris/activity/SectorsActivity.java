package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;

import com.applidium.paris.model.Sector;
import com.applidium.paris.model.SectorProvider;

import java.util.List;

public class SectorsActivity extends MapActivity {
    public static Intent makeIntent(Context context) {
        return new Intent(context, SectorsActivity.class);
    }

    @Override
    protected void onMapReady() {
        super.onMapReady();

        List<Sector> sectors = new SectorProvider().getSectors();

    }
}
