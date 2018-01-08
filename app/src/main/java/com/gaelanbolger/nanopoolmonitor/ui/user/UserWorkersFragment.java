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
import com.gaelanbolger.nanopoolmonitor.databinding.UserWorkersFragmentBinding;
import com.gaelanbolger.nanopoolmonitor.di.Injectable;
import com.gaelanbolger.nanopoolmonitor.util.AutoClearedValue;

import javax.inject.Inject;

public class UserWorkersFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private AutoClearedValue<UserWorkersFragmentBinding> binding;
    private AutoClearedValue<UserWorkersAdapter> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UserWorkersFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.user_workers_fragment, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        UserWorkersAdapter workersAdapter = new UserWorkersAdapter(dataBindingComponent);
        adapter = new AutoClearedValue<>(this, workersAdapter);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.get().workerList.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL)
        );
        binding.get().workerList.setAdapter(adapter.get());
        UserViewModel userViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(UserViewModel.class);
        userViewModel.getWorkers().observe(this, workers -> adapter.get().replace(workers));
    }
}
