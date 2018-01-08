package com.gaelanbolger.nanopoolmonitor.db;

import android.support.test.runner.AndroidJUnit4;

import com.gaelanbolger.nanopoolmonitor.util.TestUtils;
import com.gaelanbolger.nanopoolmonitor.vo.Worker;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.gaelanbolger.nanopoolmonitor.util.LiveDataTestUtils.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class WorkerDaoTest extends DbTest {

    @Test
    public void insertAndLoad() throws InterruptedException {
        final Worker worker = TestUtils.createWorker("0", "0x1");
        database.workerDao().insertAll(worker);

        final List<Worker> loaded = getValue(database.workerDao().findByAddress(worker.getUid()));
        assertThat(loaded.size(), is(1));

        final Worker replacement = TestUtils.createWorker("1", "0x1");
        database.workerDao().insertAll(replacement);

        final List<Worker> loadedReplacement = getValue(database.workerDao().findByAddress("0x1"));
        assertThat(loadedReplacement.size(), is(2));

        database.workerDao().deleteAll(loadedReplacement.toArray(new Worker[loadedReplacement.size()]));
        assertThat(getValue(database.workerDao().findByAddress("0x1")).size(), is(0));
    }
}
