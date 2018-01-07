package com.gaelanbolger.nanopoolmonitor.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gaelanbolger.nanopoolmonitor.vo.Account;
import com.gaelanbolger.nanopoolmonitor.vo.User;
import com.gaelanbolger.nanopoolmonitor.vo.Worker;

@Database(entities = {Account.class, User.class, Worker.class}, version = 1, exportSchema = false)
public abstract class NanopoolMonitorDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();

    public abstract UserDao userDao();
}
