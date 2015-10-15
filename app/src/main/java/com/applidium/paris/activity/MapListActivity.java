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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.applidium.paris.R;
import com.applidium.paris.adapter.ArrayAdapter;
import com.applidium.paris.fragment.MapFragment;

import java.util.List;

public abstract class MapListActivity extends AppCompatActivity implements MapFragment.OnMapReadyListener {

    protected MapFragment     mMapFragment;
    protected MapListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MapListPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @CallSuper
    public void onMapReady() {
        mMapFragment.centerOnParis();
    }

    public abstract class MapListAdapter<T> extends ArrayAdapter<T> implements AdapterView.OnItemClickListener {
        public MapListAdapter(List<T> elements) {
            super(elements);
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
                mListFragment.setAdapter(adapter);
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
