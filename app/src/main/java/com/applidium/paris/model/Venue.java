package com.applidium.paris.model;

import com.applidium.paris.activity.MapListActivity;
import com.applidium.paris.db.DatabaseConfig;
import com.applidium.paris.util.MapperUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties({"specials", "hereNow", "venueChains"})
@Table(databaseName = DatabaseConfig.NAME)
public class Venue extends BaseModel implements MapListActivity.MapListItem {
    public static class Wrapper {
        List<Venue> venues;
        boolean     confident;

        public List<Venue> getVenues() {
            return venues;
        }
    }

    @Column
    @PrimaryKey
    String id;
    @Column
    String name;
    Map<String, String> contact; //TODO
    @Column
    Location location;
    @Column
    String   categories;
    @Column
    boolean  verified;
    Map<String, Integer> stats; //TODO
    @Column
    String  url;
    @Column
    String  referralId;
    @Column
    String  storeId;
    @Column
    boolean allowMenuUrlEdit;
    Map<String, String> venuePage; //TODO

    public void setCategories(List<Category> categories) {
        try {
            this.categories = MapperUtil.sharedMapper().writeValueAsString(categories);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getCategories() {
        try {
            return MapperUtil.sharedMapper().readValue(categories, new TypeReference<List<Category>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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

        List<Category> categories = getCategories();
        StringBuilder sb = new StringBuilder();
        for (Category category : categories) {
            sb.append(category.toString()).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        map.put("Categories", sb.toString());

        if (location.address != null) {
            map.put("Address", location.address);
        }
        return map;
    }

    public static class Category {
        String              id;
        String              name;
        String              pluralName;
        String              shortName;
        Map<String, String> icon;
        boolean             primary;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(name).append("\n");
            sb.append("Short name: ").append(shortName).append("\n");
            sb.append("Icon:").append(icon.values().iterator().next());
            return sb.toString();
        }
    }

    public static class Location {
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
