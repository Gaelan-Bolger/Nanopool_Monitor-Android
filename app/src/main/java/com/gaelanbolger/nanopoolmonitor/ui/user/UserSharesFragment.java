package com.gaelanbolger.nanopoolmonitor.ui.user;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.binding.FragmentDataBindingComponent;
import com.gaelanbolger.nanopoolmonitor.databinding.UserSharesFragmentBinding;
import com.gaelanbolger.nanopoolmonitor.di.Injectable;
import com.gaelanbolger.nanopoolmonitor.util.AutoClearedValue;

import javax.inject.Inject;

public class UserSharesFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private AutoClearedValue<UserSharesAdapter> adapter;
    private AutoClearedValue<UserSharesFragmentBinding> binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UserSharesAdapter userSharesAdapter = new UserSharesAdapter(dataBindingComponent);
        adapter = new AutoClearedValue<>(this, userSharesAdapter);
        UserSharesFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.user_shares_fragment, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        binding.get().shareList.setAdapter(adapter.get());
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserSharesViewModel userSharesViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserSharesViewModel.class);
        userSharesViewModel.getShares().observe(this, sharesResource -> {
            binding.get().setResource(sharesResource);
            adapter.get().replace(sharesResource != null ? sharesResource.data : null);
        });
        UserViewModel userViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(UserViewModel.class);
        userViewModel.getUser().observe(this, userResource -> {
            if (userResource != null && userResource.data != null) {
                userSharesViewModel.setAddress(userResource.data.getAccount());
            }
        });
    }
}
