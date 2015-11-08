package com.applidium.map_sample.network.model;

import com.applidium.map_sample.model.Business;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties({"region"})
public class YelpResponse {
    int            total;
    List<Business> businesses;

    public List<Business> getBusinesses() {
        return businesses;
    }
}
