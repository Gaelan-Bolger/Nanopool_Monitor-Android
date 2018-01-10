package com.gaelanbolger.nanopoolmonitor;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Intent;

import com.gaelanbolger.nanopoolmonitor.data.prices.PricesService;
import com.gaelanbolger.nanopoolmonitor.di.AppInjector;
import com.gaelanbolger.nanopoolmonitor.util.AndroidUtils;
import com.gaelanbolger.nanopoolmonitor.vo.Prices;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import timber.log.Timber;

public class NanopoolMonitorApplication extends Application implements HasActivityInjector, HasServiceInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        AndroidUtils.setApplicationContext(this);
        AppInjector.init(this);

        // TODO
        if (BuildConfig.DEBUG) {
//            StrictMode.enableDefaults();
        }

        // TODO
        PricesService.enqueueWork(this, PricesService.class, 1014, new Intent(this, PricesService.class));
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }
}
