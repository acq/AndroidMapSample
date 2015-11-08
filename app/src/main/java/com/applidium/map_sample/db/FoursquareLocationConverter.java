package com.applidium.map_sample.db;

import com.applidium.map_sample.model.Venue;
import com.applidium.map_sample.util.MapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.io.IOException;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class FoursquareLocationConverter extends TypeConverter<String, Venue.Location> {
    @Override
    public String getDBValue(Venue.Location model) {
        try {
            return MapperUtil.sharedMapper().writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Venue.Location getModelValue(String data) {
        try {
            return MapperUtil.sharedMapper().readValue(data, Venue.Location.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
