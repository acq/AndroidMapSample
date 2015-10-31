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
public class Theater extends BaseModel implements MapListActivity.MapListItem {

    @Column
    String  recordId;
    @Column
    @PrimaryKey
    int     autoId;
    @Column
    String  name;
    @Column
    int     screens;
    @Column
    String  seats;
    @Column
    int     arrondissement;
    @Column
    boolean artHouse;
    @Column
    String  address;
    @Column
    double  latitude;
    @Column
    double  longitude;
    @Column
    String  source;
    @Column
    Date    updatedAt;

    Theater() {
    }

    public Theater(String recordId, int autoId, String name, int screens, String seats, int arrondissement, boolean artHouse, String address, double latitude, double longitude, String source, Date updatedAt) {
        this.recordId = recordId;
        this.autoId = autoId;
        this.name = name;
        this.screens = screens;
        this.seats = seats;
        this.arrondissement = arrondissement;
        this.artHouse = artHouse;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.source = source;
        this.updatedAt = updatedAt;
    }

    public String getRecordId() {
        return recordId;
    }

    public int getAutoId() {
        return autoId;
    }

    public String getName() {
        return name;
    }

    public int getScreens() {
        return screens;
    }

    public String getSeats() {
        return seats;
    }

    public int getArrondissement() {
        return arrondissement;
    }

    public boolean isArtHouse() {
        return artHouse;
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
        map.put("Screens", String.valueOf(screens));
        map.put("Seats", seats);
        map.put("Art & Essai", String.valueOf(artHouse));
        map.put("Address", address);
        return map;
    }
}
