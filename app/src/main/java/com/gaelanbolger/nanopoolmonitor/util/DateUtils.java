package com.gaelanbolger.nanopoolmonitor.util;

public class DateUtils {

    public static CharSequence timeAgo(long when) {
        return android.text.format.DateUtils.getRelativeTimeSpanString(when, System.currentTimeMillis(),
                android.text.format.DateUtils.MINUTE_IN_MILLIS);
    }
}
