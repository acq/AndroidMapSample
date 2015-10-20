package com.applidium.paris.network;

import com.applidium.paris.BuildConfig;
import com.applidium.paris.model.Venue;
import com.applidium.paris.network.model.FoursquareResponse;
import com.applidium.paris.network.model.YelpResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;
import retrofit.http.GET;
import retrofit.http.Query;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class ApiManager {
    private static FoursquareService sFoursquareService;
    private static YelpService       sYelpService;

    public interface FoursquareService {
        @GET("/venues/search")
        void search(@Query("ll") String coordinates, Callback<FoursquareResponse<Venue.Wrapper>> callback);
    }

    public interface YelpService {
        @GET("/search/")
        void search(@Query("location") String location, Callback<YelpResponse> callback);
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

    public static YelpService yelp() {
        if (sYelpService == null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            if (!BuildConfig.DEBUG) {
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            }

            OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(BuildConfig.YELP_CONSUMER_KEY, BuildConfig.YELP_CONSUMER_SECRET);
            consumer.setTokenWithSecret(BuildConfig.YELP_TOKEN, BuildConfig.YELP_TOKEN_SECRET);

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.interceptors().add(new SigningInterceptor(consumer));

            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BuildConfig.YELP_ENDPOINT).setConverter(new JacksonConverter(mapper)).setClient(new OkClient(okHttpClient)).build();
            if (BuildConfig.DEBUG) {
                adapter.setLogLevel(RestAdapter.LogLevel.FULL);
            }
            sYelpService = adapter.create(YelpService.class);
        }
        return sYelpService;
    }
}
