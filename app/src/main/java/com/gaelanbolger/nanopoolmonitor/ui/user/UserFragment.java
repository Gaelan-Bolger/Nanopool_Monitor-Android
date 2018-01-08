package com.gaelanbolger.nanopoolmonitor.ui.user;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.binding.FragmentDataBindingComponent;
import com.gaelanbolger.nanopoolmonitor.databinding.UserFragmentBinding;
import com.gaelanbolger.nanopoolmonitor.di.Injectable;
import com.gaelanbolger.nanopoolmonitor.util.AutoClearedValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

public class UserFragment extends Fragment implements Injectable {

    private static final String KEY_ADDRESS = "address";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private AutoClearedValue<UserFragmentBinding> binding;
    private AutoClearedValue<UserFragmentPagerAdapter> adapter;
    private UserViewModel userViewModel;

    public static UserFragment create(String address) {
        UserFragment userFragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ADDRESS, address);
        userFragment.setArguments(bundle);
        return userFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UserFragmentPagerAdapter pagerAdapter = new UserFragmentPagerAdapter(getChildFragmentManager());
        adapter = new AutoClearedValue<>(this, pagerAdapter);
        UserFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.user_fragment,
                container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(UserViewModel.class);
        userViewModel.setAddress(getArguments() == null ? null : getArguments().getString(KEY_ADDRESS));
        userViewModel.getUser().observe(this, userResource -> {
            binding.get().setUser(userResource != null ? userResource.data : null);
            binding.get().setUserResource(userResource);
            binding.get().executePendingBindings();
        });

        binding.get().pager.setAdapter(adapter.get());
        binding.get().tabs.setupWithViewPager(binding.get().pager);
        binding.get().setRetryCallback(() -> userViewModel.retry());
    }

    public class UserFragmentPagerAdapter extends FragmentStatePagerAdapter {

        private Map<Integer, Fragment> pages = new HashMap<>(getCount());

        public UserFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = pages.get(position);
            if (f == null) {
                switch (position) {
                    case 0:
                        f = new UserWorkersFragment();
                        break;
                    default:
                        f = DummyFragment.create(position);
                        break;
                }
                pages.put(position, f);
            }
            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (getItem(position) instanceof UserWorkersFragment)
                return "Workers";
            return "Page " + (position + 1);
        }
    }

    public static class DummyFragment extends Fragment {

        private Random random = new Random();

        public static Fragment create(int position) {
            DummyFragment dummyFragment = new DummyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            dummyFragment.setArguments(bundle);
            return dummyFragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);
            textView.setTextColor(Color.rgb(red, green, blue));
            textView.setText(String.valueOf(getArguments().getInt("position")));
            return textView;
        }
    }
}
