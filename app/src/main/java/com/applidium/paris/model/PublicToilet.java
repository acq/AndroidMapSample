package com.applidium.paris.model;

import com.applidium.paris.activity.MapListActivity;
import com.applidium.paris.db.DatabaseConfig;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Table(databaseName = DatabaseConfig.NAME)
public class PublicToilet extends BaseModel implements MapListActivity.MapListItem {

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

    PublicToilet() {
    }

    public PublicToilet(String recordId, String info, String label, double latitude, double longitude, Date updatedAt, String source) {
        this.recordId = recordId;
        this.info = info;
        this.label = label;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedAt = updatedAt;
        this.source = source;
    }

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

    public String getName() {
        return getInfo();
    }

    public Map<String, String> getDetails() {
        return new HashMap<>();
    }
}
