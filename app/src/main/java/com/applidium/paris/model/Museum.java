package com.applidium.paris.model;

import com.applidium.paris.db.DatabaseConfig;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(databaseName = DatabaseConfig.NAME)
public class Museum extends BaseModel {
    @PrimaryKey
    @Column
    String id;
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

    public String getId() {
        return id;
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
        return new LatLng(latitude, longitude);
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getSource() {
        return source;
    }
}
