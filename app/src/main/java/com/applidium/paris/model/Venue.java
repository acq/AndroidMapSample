package com.applidium.paris.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties({"specials", "hereNow"})
public class Venue {
    public static class Wrapper {
        List<Venue> venues;
        boolean     confident;

        public List<Venue> getVenues() {
            return venues;
        }
    }

    String               id;
    String               name;
    Map<String, String>  contact;
    Location             location;
    List<Category>       categories;
    boolean              verified;
    Map<String, Integer> stats;
    String               url;
    String               referralId;
    String               storeId;
    boolean              allowMenuUrlEdit;
    Map<String, String>  venuePage;

    public String getName() {
        return name;
    }

    public LatLng getPosition() {
        return new LatLng(location.lat, location.lng);
    }

    private static class Category {
        String              id;
        String              name;
        String              pluralName;
        String              shortName;
        Map<String, String> icon;
        boolean             primary;
    }

    private static class Location {
        String       address;
        String       crossStreet;
        double       lat;
        double       lng;
        int          distance;
        String       postalCode;
        String       cc;
        String       city;
        String       state;
        String       country;
        List<String> formattedAddress;
    }
}
