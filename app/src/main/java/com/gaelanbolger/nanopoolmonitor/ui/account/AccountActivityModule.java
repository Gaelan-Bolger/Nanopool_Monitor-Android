package com.gaelanbolger.nanopoolmonitor.ui.account;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AccountActivityModule {

    @ContributesAndroidInjector
    abstract AccountFragment contributeAccountFragment();

    @ContributesAndroidInjector
    abstract NewAccountFragment contributeNewAccountFragment();
}