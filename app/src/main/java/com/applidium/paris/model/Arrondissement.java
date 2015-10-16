package com.applidium.paris.model;

import com.applidium.paris.db.DatabaseConfig;
import com.applidium.paris.util.MapperUtil;
import com.applidium.paris.util.PolygonUtil;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.IOException;
import java.util.Date;

@Table(databaseName = DatabaseConfig.NAME)
public class Arrondissement extends BaseModel {
    @Column
    String recordId;
    @Column
    @PrimaryKey
    long   sequentialId;
    @Column
    int    number;
    @Column
    int    inseeNumber;
    @Column
    String name;
    @Column
    String officialName;
    @Column
    String source;
    @Column
    Date   updatedAt;
    @Column
    double latitude;
    @Column
    double longitude;
    @Column
    private String geoJson;
    @Column
    double length;
    @Column
    double surface;
    @Column
    double perimeter;
    GeoJson geoJsonRepresentation;

    public String getRecordId() {
        return recordId;
    }

    public long getSequentialId() {
        return sequentialId;
    }

    public int getNumber() {
        return number;
    }

    public int getInseeNumber() {
        return inseeNumber;
    }

    public String getName() {
        return name;
    }

    public String getOfficialName() {
        return officialName;
    }

    public String getSource() {
        return source;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getGeoJson() {
        return geoJson;
    }

    public double getLength() {
        return length;
    }

    public double getSurface() {
        return surface;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
        try {
            geoJsonRepresentation = MapperUtil.sharedMapper().readValue(geoJson, GeoJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LatLng getCenter() {
        if (latitude == 0 || longitude == 0) {
            return null;
        }
        return new LatLng(latitude, longitude);
    }

    public boolean contains(LatLng latLng) {
        for (int polygonIndex = 0; polygonIndex < geoJsonRepresentation.coordinates.length; polygonIndex++) {
            if (PolygonUtil.insidePolygon(geoJsonRepresentation.coordinates[polygonIndex], latLng.longitude, latLng.latitude)) {
                return true;
            }
        }
        return false;
    }
}
