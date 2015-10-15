package com.applidium.paris.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = DatabaseConfig.NAME, version = DatabaseConfig.VERSION)
public class DatabaseConfig {
    public static final String NAME = "paris_db";
    public static final int VERSION = 1;
}
