package com.applidium.paris.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtil {
    private static ObjectMapper sSharedMapper;

    public static ObjectMapper sharedMapper() {
        if (sSharedMapper == null) {
            sSharedMapper = new ObjectMapper();
            sSharedMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        }
        return sSharedMapper;
    }
}
