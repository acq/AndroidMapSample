package com.applidium.paris.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.applidium.paris.R;
import com.applidium.paris.fragment.MapFragment;

public abstract class MapListActivity extends AppCompatActivity implements MapFragment.OnMapReadyListener {

    protected MapFragment  mMapFragment;
    protected ListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MapListAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @CallSuper
    public void onMapReady() {
        mMapFragment.centerOnParis();
    }

    private class MapListAdapter extends FragmentPagerAdapter {
        public MapListAdapter(FragmentManager fm) {
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
                mListFragment = new ListFragment();
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
