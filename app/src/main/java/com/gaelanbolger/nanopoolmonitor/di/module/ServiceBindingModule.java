package com.gaelanbolger.nanopoolmonitor.di.module;

import com.gaelanbolger.nanopoolmonitor.data.prices.PricesService;
import com.gaelanbolger.nanopoolmonitor.ui.account.AccountActivity;
import com.gaelanbolger.nanopoolmonitor.ui.account.AccountActivityModule;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserActivity;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceBindingModule {

    @ContributesAndroidInjector
    abstract PricesService contributePricesService();
}