package com.gaelanbolger.nanopoolmonitor.ui.common;

import android.support.v4.app.FragmentManager;

import com.gaelanbolger.nanopoolmonitor.MainActivity;
import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.ui.scan.ScanFragment;
import com.gaelanbolger.nanopoolmonitor.ui.user.UserFragment;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {

    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToScan() {
        ScanFragment scanFragment = new ScanFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, scanFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToUser(String address) {
        String tag = "user" + "/" + address;
        UserFragment userFragment = UserFragment.create(address);
        fragmentManager.beginTransaction()
                .replace(containerId, userFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}