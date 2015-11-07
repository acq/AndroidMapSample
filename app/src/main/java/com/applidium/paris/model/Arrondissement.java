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
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Table(databaseName = DatabaseConfig.NAME)
public class Arrondissement extends BaseModel implements MapListActivity.MapListItem {
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

    Arrondissement() {
    }

    public Arrondissement(String recordId, long sequentialId, int number, int inseeNumber, String name, String officialName, String source, Date updatedAt, double latitude, double longitude, String geoJson, double length, double surface, double perimeter) {
        this.recordId = recordId;
        this.sequentialId = sequentialId;
        this.number = number;
        this.inseeNumber = inseeNumber;
        this.name = name;
        this.officialName = officialName;
        this.source = source;
        this.updatedAt = updatedAt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.geoJson = geoJson;
        this.length = length;
        this.surface = surface;
        this.perimeter = perimeter;
        try {
            geoJsonRepresentation = MapperUtil.sharedMapper().readValue(geoJson, GeoJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public LatLng getPosition() {
        return getCenter();
    }

    public boolean contains(LatLng latLng) {
        for (int polygonIndex = 0; polygonIndex < geoJsonRepresentation.coordinates.length; polygonIndex++) {
            if (PolygonUtil.insidePolygon(geoJsonRepresentation.coordinates[polygonIndex], latLng.longitude, latLng.latitude)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, String> getDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("Official name", officialName);
        map.put("Insee number", String.valueOf(inseeNumber));
        map.put("Updated at", DateFormat.getDateTimeInstance().format(updatedAt));
        map.put("Surface", String.valueOf(surface));
        return map;
    }
}
