package com.gaelanbolger.nanopoolmonitor.di;

import com.gaelanbolger.nanopoolmonitor.ui.scan.ScanFragment;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ScanFragment contributeScanFragment();

    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();
}