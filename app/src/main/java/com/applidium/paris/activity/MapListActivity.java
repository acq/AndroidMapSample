package com.applidium.paris.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.applidium.paris.R;
import com.applidium.paris.adapter.ArrayAdapter;
import com.applidium.paris.fragment.MapFragment;
import com.applidium.paris.util.TextUtil;
import com.applidium.paris.view.DirectionView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.List;

public abstract class MapListActivity extends AppCompatActivity implements MapFragment.OnMapReadyListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    protected MapFragment     mMapFragment;
    protected MapListFragment mListFragment;
    private   GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MapListPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        buildGoogleApiClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    @CallSuper
    public void onMapReady() {
        mMapFragment.centerOnParis();
        mMapFragment.getMapView().setMyLocationEnabled(true);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        requestLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        }
    }

    private void requestLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
    }

    public abstract class MapListAdapter<T> extends ArrayAdapter<T> implements AdapterView.OnItemClickListener {
        public MapListAdapter(List<T> elements) {
            super(elements);
        }

        public abstract String getTitle(int position);

        public abstract LatLng getLocation(int position);

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.direction_row, parent, false);
            }

            String title = getTitle(position);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getTitle(position));

            TextView distanceView = (TextView) convertView.findViewById(R.id.distanceText);
            DirectionView directionView = (DirectionView) convertView.findViewById(R.id.directionView);
                LatLng itemLocation = getLocation(position);
            if (mLastLocation != null && itemLocation != null) {
                LatLng userLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                double heading = SphericalUtil.computeHeading(userLocation, itemLocation);
                directionView.setHeading(heading / 180 * Math.PI);
                distanceView.setText(TextUtil.humanReadableDistance(SphericalUtil.computeDistanceBetween(userLocation, itemLocation)));
            } else {
                directionView.setHeading(null);
                distanceView.setText(null);
            }

            return convertView;
        }
    }

    public abstract MapListAdapter getAdapter();

    public static class MapListFragment extends ListFragment {
        MapListAdapter mAdapter;

        public void setAdapter(MapListAdapter adapter) {
            mAdapter = adapter;
            setListAdapter(adapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            mAdapter.onItemClick(l, v, position, id);
        }
    }

    private class MapListPagerAdapter extends FragmentPagerAdapter {
        public MapListPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                mMapFragment = new MapFragment();
                mMapFragment.setOnMapReadyListener(MapListActivity.this);
                fragment = mMapFragment;
            } else if (position == 1) {
                mListFragment = new MapListFragment();
                MapListAdapter adapter = getAdapter();
                if (adapter != null) {
                    mListFragment.setAdapter(adapter);
                }
                fragment = mListFragment;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "MAP";
            } else if (position == 1) {
                return "LIST";
            }
            return null;
        }
    }
}
