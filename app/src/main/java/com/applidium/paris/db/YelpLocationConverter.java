package com.applidium.paris.db;

import com.applidium.paris.model.Business.Location;
import com.applidium.paris.util.MapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.io.IOException;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class YelpLocationConverter extends TypeConverter<String, Location> {

    @Override
    public String getDBValue(Location model) {
        try {
            return MapperUtil.sharedMapper().writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Location getModelValue(String data) {
        try {
            return MapperUtil.sharedMapper().readValue(data, new TypeReference<Location>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
