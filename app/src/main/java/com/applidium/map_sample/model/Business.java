package com.applidium.map_sample.model;

import com.applidium.map_sample.activity.MapListActivity;
import com.applidium.map_sample.db.DatabaseConfig;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties({"deals", "gift_certificates"})
@Table(databaseName = DatabaseConfig.NAME)
public class Business extends BaseModel implements MapListActivity.MapListItem {
    @Column
    @PrimaryKey
    String         id;
    @Column
    boolean        isClaimed;
    @Column
    boolean        isClosed;
    @Column
    String         name;
    @Column
    String         imageUrl;
    @Column
    String         url;
    @Column
    String         mobileUrl;
    @Column
    String         phone;
    @Column
    String         displayPhone;
    @Column
    int            reviewCount;
    List<String[]> categories; //TODO
    @Column
    double         distance;
    @Column
    int            rating;
    @Column
    String         ratingImgUrl;
    @Column
    String         ratingImgUrlSmall;
    @Column
    String         ratingImgUrlLarge;
    @Column
    String         snippetText;
    @Column
    String         snippetImageUrl;
    @Column
    Location       location;
    @Column
    String         menuProvider;
    @Column
    String         menuDateUpdated;
    @Column
    String         reservationUrl;
    @Column
    String         eat24Url;

    public String getName() {
        return name;
    }

    public LatLng getPosition() {
        return new LatLng(location.coordinate.latitude, location.coordinate.longitude);
    }

    @Override
    public Map<String, String> getDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("Url", mobileUrl != null ? mobileUrl : url);
        map.put("Phone", phone);
        map.put("Rating", String.valueOf(rating));
        map.put("Reservation", reservationUrl);
        map.put("Address", location.address.toString());
        return map;
    }

    public static class Location {
        List<String> address;
        List<String> displayAddress;
        String       city;
        String       stateCode;
        String       postalCode;
        String       countryCode;
        String       crossStreets;
        List<String> neighborhoods;
        Coordinate   coordinate;
        float        geoAccuracy;
    }

    public static class Coordinate {
        double latitude;
        double longitude;
    }
}
