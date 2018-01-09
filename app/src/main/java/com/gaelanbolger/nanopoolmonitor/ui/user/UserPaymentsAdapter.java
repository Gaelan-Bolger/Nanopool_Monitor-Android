package com.gaelanbolger.nanopoolmonitor.ui.user;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.databinding.PaymentItemBinding;
import com.gaelanbolger.nanopoolmonitor.ui.common.DataBoundListAdapter;
import com.gaelanbolger.nanopoolmonitor.util.Objects;
import com.gaelanbolger.nanopoolmonitor.vo.Payment;

public class UserPaymentsAdapter extends DataBoundListAdapter<Payment, PaymentItemBinding> {

    private android.databinding.DataBindingComponent dataBindingComponent;

    public UserPaymentsAdapter(DataBindingComponent dataBindingComponent) {
        this.dataBindingComponent = dataBindingComponent;
    }

    @Override
    protected PaymentItemBinding createBinding(ViewGroup parent) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.payment_item, parent, false, dataBindingComponent);
    }

    @Override
    protected void bind(PaymentItemBinding binding, Payment item) {
        binding.setPayment(item);
    }

    @Override
    protected boolean areItemsTheSame(Payment oldItem, Payment newItem) {
        return Objects.equals(oldItem, newItem);
    }

    @Override
    protected boolean areContentsTheSame(Payment oldItem, Payment newItem) {
        return Objects.equals(oldItem, newItem);
    }
}
