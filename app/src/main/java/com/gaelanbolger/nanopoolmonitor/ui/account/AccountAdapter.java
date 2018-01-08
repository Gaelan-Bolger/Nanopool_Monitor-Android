package com.gaelanbolger.nanopoolmonitor.ui.account;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.databinding.AccountItemBinding;
import com.gaelanbolger.nanopoolmonitor.ui.common.DataBoundListAdapter;
import com.gaelanbolger.nanopoolmonitor.util.Objects;
import com.gaelanbolger.nanopoolmonitor.vo.Account;

public class AccountAdapter extends DataBoundListAdapter<Account, AccountItemBinding> {

    public interface AccountClickCallback {
        void onClick(Account account);
    }

    private DataBindingComponent dataBindingComponent;
    private AccountClickCallback callback;

    public AccountAdapter(DataBindingComponent dataBindingComponent, AccountClickCallback callback) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
    }

    @Override
    protected AccountItemBinding createBinding(ViewGroup parent) {
        AccountItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.account_item, parent, false, dataBindingComponent);
        binding.getRoot().setOnClickListener((v) -> {
            Account account = binding.getAccount();
            if (account != null && callback != null) {
                callback.onClick(account);
            }
        });
        return binding;
    }

    @Override
    protected void bind(AccountItemBinding binding, Account item) {
        binding.setAccount(item);
    }

    @Override
    protected boolean areItemsTheSame(Account oldItem, Account newItem) {
        return areContentsTheSame(oldItem, newItem);
    }

    @Override
    protected boolean areContentsTheSame(Account oldItem, Account newItem) {
        return Objects.equals(oldItem.getAddress(), newItem.getAddress());
    }
}
