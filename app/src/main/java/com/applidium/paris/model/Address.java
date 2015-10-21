package com.applidium.paris.model;

import com.applidium.paris.db.DatabaseConfig;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(databaseName = DatabaseConfig.NAME)
public class Address extends BaseModel {
    @Column
    @PrimaryKey
    String recordid;
    @Column
    double latitude;
    @Column
    double longitude;
    @Column
    String address;

    public String getAddress() {
        return address;
    }

    public LatLng getPosition() {
        return new LatLng(latitude, longitude);
    }
}
