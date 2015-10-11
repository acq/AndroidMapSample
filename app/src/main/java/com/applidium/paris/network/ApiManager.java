package com.applidium.paris.network;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import com.applidium.paris.BuildConfig;
import com.applidium.paris.App;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class ApiManager {
    private static ApiManagerService sApiManagerService;

    public interface ApiManagerService {

    }

    public static ApiManagerService getApiManager() {
        if (sApiManagerService == null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            if (!BuildConfig.DEBUG) {
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            }

            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BuildConfig.ENDPOINT).setConverter(new JacksonConverter(mapper)).build();
            if (BuildConfig.DEBUG) {
                adapter.setLogLevel(RestAdapter.LogLevel.FULL);
            }
            sApiManagerService = adapter.create(ApiManagerService.class);
        }
        return sApiManagerService;
    }
}
