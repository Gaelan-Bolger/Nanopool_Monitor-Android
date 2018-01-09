package com.gaelanbolger.nanopoolmonitor.ui.user;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UserActivityModule {

    @ContributesAndroidInjector
    abstract UserWorkersFragment contributeUserWorkersFragment();

    @ContributesAndroidInjector
    abstract UserPaymentsFragment contributeUserPaymentsFragment();

    @ContributesAndroidInjector
    abstract UserSharesFragment contributeUserSharesFragment();
}