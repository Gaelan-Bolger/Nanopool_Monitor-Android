package com.gaelanbolger.nanopoolmonitor.ui.user;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.databinding.UserActivityBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class UserActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String KEY_ADDRESS = "address";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    UserNavigationController navigationController;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private String address;
    private UserActivityBinding binding;
    private UserViewModel userViewModel;

    public static void start(Context context, String address) {
        Intent starter = new Intent(context, UserActivity.class);
        starter.putExtra(KEY_ADDRESS, address);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            address = savedInstanceState.getString(KEY_ADDRESS, address);
        } else if (getIntent() != null && getIntent().hasExtra(KEY_ADDRESS)) {
            address = getIntent().getStringExtra(KEY_ADDRESS);
        }

        binding = DataBindingUtil.setContentView(this, R.layout.user_activity);
        binding.setAddress(address);

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        userViewModel.setAddress(address);
        userViewModel.getUser().observe(this, userResource -> {
            binding.setUser(userResource != null ? userResource.data : null);
            binding.executePendingBindings();
        });
        binding.pager.setAdapter(new UserFragmentPagerAdapter(getSupportFragmentManager()));
        binding.tabs.setupWithViewPager(binding.pager);
    }

    @Override
    protected void onDestroy() {
        binding.unbind();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
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
