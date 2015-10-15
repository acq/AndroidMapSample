package com.applidium.paris.model;

import com.applidium.paris.db.DatabaseConfig;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(databaseName = DatabaseConfig.NAME)
public class Sector extends BaseModel {

    @PrimaryKey
    @Column
    public long sequentialId;
    @Column
    public String recordId;
    @Column
    public int    number;
    @Column
    public long   inseeNumber;
    @Column
    public String name;
    @Column
    public Date   updatedAt;
    @Column
    public String source;
    @Column
    public int    arrondissement;
    @Column
    public long   arrondissementId;
    @Column
    public double perimeter;
    @Column
    public double length;
    @Column
    public double surface;
    @Column
    public String geoJson;

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
}
