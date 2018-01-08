package com.gaelanbolger.nanopoolmonitor.di;

import com.gaelanbolger.nanopoolmonitor.ui.account.AccountFragment;
import com.gaelanbolger.nanopoolmonitor.ui.account.NewAccountFragment;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserFragment;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserWorkersFragment;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserWorkersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract AccountFragment contributeAccountFragment();

    @ContributesAndroidInjector
    abstract NewAccountFragment contributeNewAccountFragment();

    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();

    @ContributesAndroidInjector
    abstract UserWorkersFragment contributeWorkerFragment();
}