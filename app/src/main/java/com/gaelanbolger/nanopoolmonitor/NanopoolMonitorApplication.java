package com.gaelanbolger.nanopoolmonitor;

import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;

import com.gaelanbolger.nanopoolmonitor.di.AppInjector;
import com.gaelanbolger.nanopoolmonitor.util.AndroidUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class NanopoolMonitorApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        AndroidUtils.setApplicationContext(this);
        AppInjector.init(this);

        if (BuildConfig.DEBUG) {
//            StrictMode.enableDefaults();
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
