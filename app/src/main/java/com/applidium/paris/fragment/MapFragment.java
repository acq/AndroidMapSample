package com.applidium.paris.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.android.airmapview.AirMapView;
import com.applidium.paris.R;
import com.google.android.gms.maps.model.LatLng;

public class MapFragment extends Fragment {

    protected AirMapView mMapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        mMapView = (AirMapView) view.findViewById(R.id.map);
        mMapView.initialize(getFragmentManager());

        return view;
    }

    public AirMapView getMapView() {
        return mMapView;
    }

    public void centerOnParis() {
        mMapView.setCenterZoom(new LatLng(48.8567, 2.3508), 12);
    }

}
