package com.applidium.paris.model;

import com.applidium.paris.db.DatabaseConfig;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(databaseName = DatabaseConfig.NAME)
public class PublicToilet extends BaseModel {

    @Column
    @PrimaryKey
    String recordId;
    @Column
    String info;
    @Column
    String label;
    @Column
    double latitude;
    @Column
    double longitude;
    @Column
    Date   updatedAt;
    @Column
    String source;

    public String getRecordId() {
        return recordId;
    }

    public String getInfo() {
        return info;
    }

    public String getLabel() {
        return label;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getSource() {
        return source;
    }

    public LatLng getPosition() {
        return new LatLng(latitude, longitude);
    }
}
