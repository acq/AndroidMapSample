package com.applidium.paris.model;

import com.applidium.paris.db.DatabaseConfig;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(databaseName = DatabaseConfig.NAME)
@JsonIgnoreProperties({"tags", "link"})
public class Event extends BaseModel {
    @Column
    double latitude;
    @Column
    double longitude;
    @Column
    String lang;
    @Column
    String city;
    @Column
    @PrimaryKey
    String uid;
    @Column
    String placename;
    @Column
    String pricing_info;
    @Column
    String image;
    @Column
    Date   date_start;
    @Column
    Date   updated_at;
    @Column
    String space_time_info;
    @Column
    String program_uid;
    @Column
    String image_thumb;
    @Column
    String free_text;
    @Column
    String address;
    @Column
    String department;
    @Column
    String title;
    @Column
    String region;
    @Column
    Date   date_end;
    @Column
    String description;

    public void setLatlon(double[] latlon) {
        latitude = latlon[0];
        longitude = latlon[1];
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLang() {
        return lang;
    }

    public String getUid() {
        return uid;
    }

    public String getPlacename() {
        return placename;
    }

    public String getPricing_info() {
        return pricing_info;
    }

    public String getImage() {
        return image;
    }

    public Date getDate_start() {
        return date_start;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public String getSpace_time_info() {
        return space_time_info;
    }

    public String getProgram_uid() {
        return program_uid;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public String getFree_text() {
        return free_text;
    }

    public String getAddress() {
        return address;
    }

    public String getDepartment() {
        return department;
    }

    public String getTitle() {
        return title;
    }

    public String getRegion() {
        return region;
    }

    public Date getDate_end() {
        return date_end;
    }

    public String getDescription() {
        return description;
    }
}
