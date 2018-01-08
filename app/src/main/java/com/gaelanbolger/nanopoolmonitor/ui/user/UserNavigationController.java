package com.gaelanbolger.nanopoolmonitor.ui.user;

import android.support.v4.app.FragmentManager;

import com.gaelanbolger.nanopoolmonitor.R;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link UserActivity}.
 */
public class UserNavigationController {

    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public UserNavigationController(UserActivity userActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = userActivity.getSupportFragmentManager();
    }

    public void popBackStack() {
        fragmentManager.popBackStack();
    }
}