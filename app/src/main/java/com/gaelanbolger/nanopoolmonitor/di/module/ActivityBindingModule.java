package com.gaelanbolger.nanopoolmonitor.di.module;

import com.gaelanbolger.nanopoolmonitor.di.ActivityScope;
import com.gaelanbolger.nanopoolmonitor.ui.account.AccountActivity;
import com.gaelanbolger.nanopoolmonitor.ui.account.AccountActivityModule;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserActivity;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = AccountActivityModule.class)
    abstract AccountActivity contributeMainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = UserActivityModule.class)
    abstract UserActivity contributeUserActivity();
}