package com.gaelanbolger.nanopoolmonitor.util;

import com.gaelanbolger.nanopoolmonitor.vo.AvgHashrate;
import com.gaelanbolger.nanopoolmonitor.vo.User;
import com.gaelanbolger.nanopoolmonitor.vo.Worker;

public class TestUtils {

    public static final String UNCONFIRMED_BALANCE = "0.00000000";
    public static final String BALANCE = "0.10000000001";
    public static final String HASH_RATE = "17.0";

    public static User createUser(String account) {
        return new User(account, UNCONFIRMED_BALANCE, BALANCE, HASH_RATE,
                new AvgHashrate(HASH_RATE, HASH_RATE, HASH_RATE, HASH_RATE, HASH_RATE));
    }

    public static Worker createWorker(String id, String uid) {
        return new Worker(id, uid, HASH_RATE, System.currentTimeMillis(), 1000,
                HASH_RATE, HASH_RATE, HASH_RATE, HASH_RATE, HASH_RATE);
    }
}
