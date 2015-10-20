package com.applidium.paris.model;

import com.airbnb.android.airmapview.AirMapMarker;
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
public class Museum extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column
    long id;
    @Column
    String recordId;
    @Column
    String name;
    @Column
    String address;
    @Column
    String openingHours;
    @Column
    String city;
    @Column
    String region;
    @Column
    String website;
    @Column
    String closedDays;
    @Column
    String closed;
    @Column
    String reopening;
    @Column
    String lateNightOpenings;
    @Column
    String postalCode;
    @Column
    String department;
    @Column
    double latitude;
    @Column
    double longitude;
    @Column
    String annex;
    @Column
    Date   updatedAt;
    @Column
    String source;

    public long getId() {
        return id;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getWebsite() {
        return website;
    }

    public String getClosedDays() {
        return closedDays;
    }

    public String getClosed() {
        return closed;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getDepartment() {
        return department;
    }

    public LatLng getCoordinates() {
        if (latitude == 0 || longitude == 0) {
            return null;
        }
        return new LatLng(latitude, longitude);
    }

    public Map<String, String> getDetailsBundle() {
        Map<String, String> map = new HashMap<>();
        map.put("Name", getName());
        map.put("Address", String.format("%s, %s %s", getAddress(), getPostalCode(), getCity()));
        map.put("Opening hours", getOpeningHours());
        map.put("Closed days", getClosedDays());
        map.put("Web site", getWebsite());
        return map;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getSource() {
        return source;
    }

    public AirMapMarker<Museum> getMarker() {
        AirMapMarker.Builder<Museum> builder = new AirMapMarker.Builder<Museum>();
        builder.object(this);
        builder.title(getName());
        builder.position(getCoordinates());
        builder.id(getId());
        return builder.build();
    }
}
