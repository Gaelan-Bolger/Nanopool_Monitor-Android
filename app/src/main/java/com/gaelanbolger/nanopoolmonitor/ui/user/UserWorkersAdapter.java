package com.gaelanbolger.nanopoolmonitor.ui.user;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.databinding.WorkerItemBinding;
import com.gaelanbolger.nanopoolmonitor.ui.common.DataBoundListAdapter;
import com.gaelanbolger.nanopoolmonitor.util.Objects;
import com.gaelanbolger.nanopoolmonitor.vo.Worker;

public class UserWorkersAdapter extends DataBoundListAdapter<Worker, WorkerItemBinding> {

    private DataBindingComponent dataBindingComponent;

    public UserWorkersAdapter(DataBindingComponent dataBindingComponent) {
        this.dataBindingComponent = dataBindingComponent;
    }

    @Override
    protected WorkerItemBinding createBinding(ViewGroup parent) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.worker_item, parent, false, dataBindingComponent);
    }

    @Override
    protected void bind(WorkerItemBinding binding, Worker item) {
        binding.setNumber(bindPosition + 1);
        binding.setWorker(item);
    }

    @Override
    protected boolean areItemsTheSame(Worker oldItem, Worker newItem) {
        return Objects.equals(oldItem, newItem);
    }

    @Override
    protected boolean areContentsTheSame(Worker oldItem, Worker newItem) {
        return Objects.equals(oldItem, newItem);
    }
}
