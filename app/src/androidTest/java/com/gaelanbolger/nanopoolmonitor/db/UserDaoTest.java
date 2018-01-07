package com.gaelanbolger.nanopoolmonitor.db;

import android.support.test.runner.AndroidJUnit4;

import com.gaelanbolger.nanopoolmonitor.util.TestUtils;
import com.gaelanbolger.nanopoolmonitor.vo.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.gaelanbolger.nanopoolmonitor.util.LiveDataTestUtils.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest extends DbTest {

    @Test
    public void insertAndLoad() throws InterruptedException {
        final User user = TestUtils.createUser("0x1");
        database.userDao().insert(user);

        final User loaded = getValue(database.userDao().findByAccount(user.getAccount()));
        assertThat(loaded.getAccount(), is(user.getAccount()));

        final User replacement = TestUtils.createUser("0x2");
        database.userDao().insert(replacement);

        final User loadedReplacement = getValue(database.userDao().findByAccount("0x2"));
        assertThat(loadedReplacement.getAccount(), is("0x2"));
    }
}
