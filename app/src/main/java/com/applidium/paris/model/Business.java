package com.applidium.paris.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

@JsonIgnoreProperties({"deals", "gift_certificates"})
public class Business {
    String         id;
    boolean        isClaimed;
    boolean        isClosed;
    String         name;
    String         imageUrl;
    String         url;
    String         mobileUrl;
    String         phone;
    String         displayPhone;
    int            reviewCount;
    List<String[]> categories;
    double         distance;
    int            rating;
    String         ratingImgUrl;
    String         ratingImgUrlSmall;
    String         ratingImgUrlLarge;
    String         snippetText;
    String         snippetImageUrl;
    Location       location;
    String         menuProvider;
    String         menuDateUpdated;
    String         reservationUrl;
    String         eat24Url;

    public String getName() {
        return name;
    }

    public LatLng getPosition() {
        return new LatLng(location.coordinate.latitude, location.coordinate.longitude);
    }

    private static class Location {
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

    private static class Coordinate {
        double latitude;
        double longitude;
    }
}