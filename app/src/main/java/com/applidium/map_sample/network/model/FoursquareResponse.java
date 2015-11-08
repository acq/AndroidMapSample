package com.applidium.map_sample.network.model;

public class FoursquareResponse<T> {
    Meta meta;
    T response;

    private static class Meta {
        int code;
        String requestId;
    }

    public T getResponse() {
        return response;
    }
}
