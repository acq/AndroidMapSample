package com.applidium.paris.model;

import com.applidium.paris.activity.MapListActivity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties({"specials", "hereNow"})
public class Venue implements MapListActivity.MapListItem {
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

    @Override
    public Map<String, String> getDetails() {
        Map<String, String> map = new HashMap<>();
        if (url != null) {

            map.put("Url", url);
        }
        map.put("Categories", categories.toString());
        if (location.address != null) {
            map.put("Address", location.address);
        }
        return map;
    }

    private static class Category {
        String              id;
        String              name;
        String              pluralName;
        String              shortName;
        Map<String, String> icon;
        boolean             primary;

        @Override
        public String toString() {
            return name;
        }
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
