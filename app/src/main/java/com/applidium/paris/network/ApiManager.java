package com.applidium.paris.network;

import com.applidium.paris.BuildConfig;
import com.applidium.paris.model.Venue;
import com.applidium.paris.network.model.FoursquareResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;
import retrofit.http.GET;
import retrofit.http.Query;

public class ApiManager {
    private static FoursquareService sFoursquareService;

    public interface FoursquareService {
        @GET("/venues/search")
        void search(@Query("ll") String coordinates, Callback<FoursquareResponse<Venue.Wrapper>> callback);
    }

    public static FoursquareService foursquare() {
        if (sFoursquareService == null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            if (!BuildConfig.DEBUG) {
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            }

            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BuildConfig.FOURSQUARE_ENDPOINT).setConverter(new JacksonConverter(mapper)).setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addQueryParam("client_id", BuildConfig.FOURSQUARE_CLIENT_ID);
                    request.addQueryParam("client_secret", BuildConfig.FOURSQUARE_CLIENT_SECRET);
                    request.addQueryParam("v", "20130815");
                }
            }).build();
            if (BuildConfig.DEBUG) {
                adapter.setLogLevel(RestAdapter.LogLevel.FULL);
            }
            sFoursquareService = adapter.create(FoursquareService.class);
        }
        return sFoursquareService;
    }
}
