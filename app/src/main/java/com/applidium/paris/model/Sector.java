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
}
