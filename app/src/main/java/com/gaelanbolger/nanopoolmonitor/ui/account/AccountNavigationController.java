package com.gaelanbolger.nanopoolmonitor.ui.account;

import android.support.v4.app.FragmentManager;

import com.gaelanbolger.nanopoolmonitor.R;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link AccountActivity}.
 */
public class AccountNavigationController {

    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public AccountNavigationController(AccountActivity accountActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = accountActivity.getSupportFragmentManager();
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

    public void popBackStack() {
        fragmentManager.popBackStack();
    }
}