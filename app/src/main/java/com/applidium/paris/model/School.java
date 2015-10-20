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
