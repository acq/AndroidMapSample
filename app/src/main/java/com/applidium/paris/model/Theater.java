package com.applidium.paris.model;

import com.applidium.paris.db.DatabaseConfig;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(databaseName = DatabaseConfig.NAME)
public class Theater extends BaseModel {

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
}
