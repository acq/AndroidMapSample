package com.applidium.map_sample.model;

import com.applidium.map_sample.activity.MapListActivity;
import com.applidium.map_sample.db.DatabaseConfig;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Table(databaseName = DatabaseConfig.NAME)
public class School extends BaseModel implements MapListActivity.MapListItem {
    @Column
    String recordId;
    @Column
    @PrimaryKey
    String id;
    @Column
    String name;
    @Column
    String type;
    @Column
    String address;
    @Column
    double latitude;
    @Column
    double longitude;
    @Column
    String city;
    @Column
    int    postalCode;
    @Column
    String source;
    @Column
    Date   updatedAt;

    School() {
    }

    public School(String recordId, String id, String name, String type, String address, double latitude, double longitude, String city, int postalCode, String source, Date updatedAt) {
        this.recordId = recordId;
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.postalCode = postalCode;
        this.source = source;
        this.updatedAt = updatedAt;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getSource() {
        return source;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public LatLng getPosition() {
        if (latitude == 0 || longitude == 0) {
            return null;
        }
        return new LatLng(latitude, longitude);
    }

    @Override
    public Map<String, String> getDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("Address", address);
        map.put("Type", type);
        return map;
    }
}
