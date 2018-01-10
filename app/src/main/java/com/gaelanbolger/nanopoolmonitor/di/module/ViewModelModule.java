package com.gaelanbolger.nanopoolmonitor.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.gaelanbolger.nanopoolmonitor.di.ViewModelKey;
import com.gaelanbolger.nanopoolmonitor.ui.account.AccountViewModel;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserPaymentsViewModel;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserSharesViewModel;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserViewModel;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserWorkersViewModel;
import com.gaelanbolger.nanopoolmonitor.viewmodel.NanopoolViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel.class)
    abstract ViewModel bindAccountViewModel(AccountViewModel accountViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserWorkersViewModel.class)
    abstract ViewModel bindUserWorkersViewModel(UserWorkersViewModel userWorkersViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserPaymentsViewModel.class)
    abstract ViewModel bindUserPaymentsViewModel(UserPaymentsViewModel userPaymentsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserSharesViewModel.class)
    abstract ViewModel bindUserSharesViewModel(UserSharesViewModel userSharesViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(NanopoolViewModelFactory factory);
}
