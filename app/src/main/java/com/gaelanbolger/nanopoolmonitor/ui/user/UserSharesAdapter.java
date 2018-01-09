package com.gaelanbolger.nanopoolmonitor.ui.user;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.databinding.ShareItemBinding;
import com.gaelanbolger.nanopoolmonitor.ui.common.DataBoundListAdapter;
import com.gaelanbolger.nanopoolmonitor.util.Objects;
import com.gaelanbolger.nanopoolmonitor.vo.Share;

public class UserSharesAdapter extends DataBoundListAdapter<Share, ShareItemBinding> {

    private DataBindingComponent dataBindingComponent;

    public UserSharesAdapter(DataBindingComponent dataBindingComponent) {
        this.dataBindingComponent = dataBindingComponent;
    }

    @Override
    protected ShareItemBinding createBinding(ViewGroup parent) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.share_item,
                parent, false, dataBindingComponent);
    }

    @Override
    protected void bind(ShareItemBinding binding, Share item) {
        binding.setShare(item);
    }

    @Override
    protected boolean areItemsTheSame(Share oldItem, Share newItem) {
        return Objects.equals(oldItem, newItem);
    }

    @Override
    protected boolean areContentsTheSame(Share oldItem, Share newItem) {
        return Objects.equals(oldItem, newItem);
    }
}
