package com.gaelanbolger.nanopoolmonitor.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.gaelanbolger.nanopoolmonitor.ui.account.AccountViewModel;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserViewModel;
import com.gaelanbolger.nanopoolmonitor.viewmodel.NanopoolViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel.class)
    abstract ViewModel bindAccountViewModel(AccountViewModel accountViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(NanopoolViewModelFactory factory);
}
