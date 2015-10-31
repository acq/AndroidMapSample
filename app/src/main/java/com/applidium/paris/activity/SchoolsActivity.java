package com.applidium.paris.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.applidium.paris.model.School;
import com.applidium.paris.db.SchoolRepository;

public class SchoolsActivity extends MapListActivity<School> {

    public static Intent makeIntent(Context context) {
        return new Intent(context, SchoolsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItems(new SchoolRepository().getSchools());
    }
}
