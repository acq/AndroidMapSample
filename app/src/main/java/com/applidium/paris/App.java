package com.applidium.paris;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;

import com.raizlabs.android.dbflow.config.FlowManager;

import java.sql.SQLException;

public class App extends Application {

    private static Activity mProgressActivity;
    private static int mProgressRequested = 0;

    private static App sInstance;

    public App() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }

    public static App getContext() {
        return sInstance;
    }

    public static void showProgressAction(final Activity activity) {
        if (activity != mProgressActivity) {
            mProgressRequested = 0;
            mProgressActivity = activity;
        }
        mProgressRequested++;
        if (activity != null) {
            activity.setProgressBarIndeterminateVisibility(true);
        }
    }

    public static void hideProgressAction(final Activity activity) {
        mProgressRequested--;
        if (mProgressRequested <= 0) {
            mProgressActivity = null;
            mProgressRequested = 0;
            if (activity != null) {
                activity.setProgressBarIndeterminateVisibility(false);
            }
        }
    }

    public static boolean isRunningOnPhone() {
        int screenCategory = getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenCategory == Configuration.SCREENLAYOUT_SIZE_SMALL || screenCategory == Configuration.SCREENLAYOUT_SIZE_NORMAL;
    }

    public static boolean isPortrait() {
        return sInstance.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
