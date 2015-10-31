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
    String address;
    @Column
    double latitude;
    @Column
    double longitude;

    Address() {
    }

    public Address(String recordid, String address, double latitude, double longitude) {
        this.recordid = recordid;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getPosition() {
        return new LatLng(latitude, longitude);
    }
}
