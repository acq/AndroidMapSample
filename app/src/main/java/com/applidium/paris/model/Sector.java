package com.applidium.paris.model;

import com.applidium.paris.activity.MapListActivity;
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
import java.util.HashMap;
import java.util.Map;

@Table(databaseName = DatabaseConfig.NAME)
public class Sector extends BaseModel implements MapListActivity.MapListItem {

    @PrimaryKey
    @Column
    long   sequentialId;
    @Column
    String recordId;
    @Column
    int    number;
    @Column
    long   inseeNumber;
    @Column
    String name;
    @Column
    Date   updatedAt;
    @Column
    String source;
    @Column
    int    arrondissement;
    @Column
    long   arrondissementId;
    @Column
    double perimeter;
    @Column
    double length;
    @Column
    double surface;
    @Column
    private String geoJson;
    @Column
    double centerLatitude;
    @Column
    double centerLongitude;
    GeoJson geoJsonRepresentation;


    public long getSequentialId() {
        return sequentialId;
    }

    public String getRecordId() {
        return recordId;
    }

    public int getNumber() {
        return number;
    }

    public long getInseeNumber() {
        return inseeNumber;
    }

    public String getName() {
        return name;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getSource() {
        return source;
    }

    public int getArrondissement() {
        return arrondissement;
    }

    public long getArrondissementId() {
        return arrondissementId;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public double getLength() {
        return length;
    }

    public double getSurface() {
        return surface;
    }

    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
        try {
            geoJsonRepresentation = MapperUtil.sharedMapper().readValue(geoJson, GeoJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean contains(LatLng latLng) {
        for (int polygonIndex = 0; polygonIndex < geoJsonRepresentation.coordinates.length; polygonIndex++) {
            if (PolygonUtil.insidePolygon(geoJsonRepresentation.coordinates[polygonIndex], latLng.longitude, latLng.latitude)) {
                return true;
            }
        }
        return false;
    }

    public LatLng getCenter() {
        return new LatLng(centerLatitude, centerLongitude);
    }

    @Override
    public LatLng getPosition() {
        return getCenter();
    }

    @Override
    public Map<String, String> getDetails() {
        Map<String,String> map = new HashMap<>();
        map.put("Surface", String.valueOf(surface));
        map.put("Perimeter", String.valueOf(perimeter));
        return map;
    }
}
