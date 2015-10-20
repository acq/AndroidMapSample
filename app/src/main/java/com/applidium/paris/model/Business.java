package com.applidium.paris.model;

import com.applidium.paris.activity.MapListActivity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties({"deals", "gift_certificates"})
public class Business implements MapListActivity.MapListItem {
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