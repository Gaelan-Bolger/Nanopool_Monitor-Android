package com.gaelanbolger.nanopoolmonitor.binding;

import android.databinding.BindingAdapter;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data Binding adapters specific to the app.
 */
public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("dateTime")
    public static void setDateTimeText(TextView view, long millis) {
        view.setText(SimpleDateFormat.getDateTimeInstance().format(new Date(millis)));
    }

    @BindingAdapter("timeAgo")
    public static void setTimeAgoText(TextView view, long millis) {
        view.setText(DateUtils.getRelativeTimeSpanString(millis, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));
    }
}