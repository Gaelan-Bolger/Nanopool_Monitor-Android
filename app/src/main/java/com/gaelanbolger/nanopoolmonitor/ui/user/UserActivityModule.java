package com.gaelanbolger.nanopoolmonitor.ui.user;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UserActivityModule {

    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();

    @ContributesAndroidInjector
    abstract UserWorkersFragment contributeUserWorkersFragment();
}