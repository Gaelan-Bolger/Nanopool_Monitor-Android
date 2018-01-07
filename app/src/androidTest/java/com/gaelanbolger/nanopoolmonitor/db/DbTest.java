package com.gaelanbolger.nanopoolmonitor.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;

abstract public class DbTest {

    protected NanopoolMonitorDatabase database;

    @Before
    public void initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                NanopoolMonitorDatabase.class).build();
    }

    @After
    public void closeDb() {
        database.close();
    }
}
