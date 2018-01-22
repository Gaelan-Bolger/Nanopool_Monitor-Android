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
import com.gaelanbolger.nanopoolmonitor.vo.Share;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        userViewModel.setAddress(address);
        userViewModel.getUser().observe(this, userResource -> {
            binding.setUser(userResource != null ? userResource.data : null);
            binding.setUserResource(userResource);
            binding.executePendingBindings();
        });
        userViewModel.getChartData().observe(this, sharesResource -> {
            if (sharesResource != null) {
                List<Share> shares;
                if ((shares = sharesResource.data) != null && shares.size() > 0) {
                    List<Entry> entries = new ArrayList<>(50);
                    for (int i = 0; i < shares.size() && i < 50; i++) {
                        entries.add(new Entry(i, shares.get(i).getShares()));
                    }
                    LineDataSet lineDataSet = new LineDataSet(entries, "Shares");
                    lineDataSet.setDrawCircles(false);
                    lineDataSet.setDrawValues(false);
                    lineDataSet.setColor(Color.WHITE);
                    lineDataSet.setLineWidth(1f);
                    if (binding.chart.getData() == null)
                        binding.chart.animateX(1500);
                    binding.chart.setData(new LineData(lineDataSet));
                    binding.chart.invalidate();
                }
            }
        });
        binding.chart.setTouchEnabled(false);
        binding.chart.setScaleEnabled(false);
        binding.chart.setDrawBorders(false);
        binding.chart.setDrawGridBackground(false);
        binding.chart.getDescription().setEnabled(false);
        binding.chart.getLegend().setEnabled(false);
        binding.chart.getXAxis().setEnabled(false);
        binding.chart.getAxisRight().setEnabled(false);
        binding.chart.getAxisLeft().setDrawAxisLine(false);
        binding.chart.getAxisLeft().setDrawGridLines(false);
        binding.chart.getAxisLeft().setTextColor(Color.WHITE);
        binding.chart.getAxisLeft().setLabelCount(1, true);
        binding.setRetryCallback(() -> userViewModel.retry());
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
                    case 1:
                        f = new UserPaymentsFragment();
                        break;
                    case 2:
                        f = new UserSharesFragment();
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
            Fragment page = getItem(position);
            if (page instanceof UserWorkersFragment)
                return "Workers";
            else if (page instanceof UserPaymentsFragment)
                return "Payments";
            else if (page instanceof UserSharesFragment)
                return "Shares";
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
