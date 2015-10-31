package com.applidium.paris.model;

import com.airbnb.android.airmapview.AirMapMarker;
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
public class Museum extends BaseModel implements MapListActivity.MapListItem {
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
    String postalCode;
    @Column
    String department;
    @Column
    double latitude;
    @Column
    double longitude;
    @Column
    String lateNightOpenings;
    @Column
    String reopening;
    @Column
    String annex;
    @Column
    Date   updatedAt;
    @Column
    String source;

    Museum() {
    }

    public Museum(String recordId, String name, String address, String openingHours, String city, String region, String website, String closedDays, String closed, String postalCode, String department, double latitude, double longitude, String lateNightOpenings, String reopening, String annex, Date updatedAt, String source) {
        this.recordId = recordId;
        this.name = name;
        this.address = address;
        this.openingHours = openingHours;
        this.city = city;
        this.region = region;
        this.website = website;
        this.closedDays = closedDays;
        this.closed = closed;
        this.postalCode = postalCode;
        this.department = department;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lateNightOpenings = lateNightOpenings;
        this.reopening = reopening;
        this.annex = annex;
        this.updatedAt = updatedAt;
        this.source = source;
    }

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

    @Override
    public LatLng getPosition() {
        if (latitude == 0 || longitude == 0) {
            return null;
        }
        return new LatLng(latitude, longitude);
    }

    @Override
    public Map<String, String> getDetails() {
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
        builder.position(getPosition());
        builder.id(getId());
        return builder.build();
    }
}
