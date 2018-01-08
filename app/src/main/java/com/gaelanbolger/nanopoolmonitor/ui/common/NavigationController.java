package com.gaelanbolger.nanopoolmonitor.ui.common;

import android.support.v4.app.FragmentManager;

import com.gaelanbolger.nanopoolmonitor.MainActivity;
import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.ui.account.AccountFragment;
import com.gaelanbolger.nanopoolmonitor.ui.account.NewAccountFragment;
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

    public void navigateToAccount(String address) {
        AccountFragment fragment = AccountFragment.create(address);
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void navigateToNewAccount() {
        NewAccountFragment fragment = NewAccountFragment.create();
        fragmentManager.beginTransaction()
                .add(containerId, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToUser(String address) {
        String tag = "user" + "/" + address;
        UserFragment fragment = UserFragment.create(address);
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void popBackStack() {
        fragmentManager.popBackStack();
    }
}