package com.gaelanbolger.nanopoolmonitor.ui.scan;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.databinding.AccountItemBinding;
import com.gaelanbolger.nanopoolmonitor.vo.Account;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.Holder> {

    public interface AccountClickCallback {
        void onClick(Account account);
    }

    private final DataBindingComponent dataBindingComponent;
    private final AccountClickCallback callback;

    private List<Account> items;

    public AccountAdapter(DataBindingComponent dataBindingComponent, AccountClickCallback callback) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        AccountItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.account_item, parent, false, dataBindingComponent);
        binding.getRoot().setOnClickListener((v) -> {
            Account account = binding.getAccount();
            if (account != null && callback != null) {
                callback.onClick(account);
            }
        });
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.binding.setAccount(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(List<Account> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private final AccountItemBinding binding;

        public Holder(AccountItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
