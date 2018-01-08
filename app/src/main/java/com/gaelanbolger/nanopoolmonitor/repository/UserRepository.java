package com.gaelanbolger.nanopoolmonitor.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.gaelanbolger.nanopoolmonitor.AppExecutors;
import com.gaelanbolger.nanopoolmonitor.api.ApiResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolService;
import com.gaelanbolger.nanopoolmonitor.db.UserDao;
import com.gaelanbolger.nanopoolmonitor.db.WorkerDao;
import com.gaelanbolger.nanopoolmonitor.util.RateLimiter;
import com.gaelanbolger.nanopoolmonitor.vo.Resource;
import com.gaelanbolger.nanopoolmonitor.vo.User;
import com.gaelanbolger.nanopoolmonitor.vo.Worker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository that handles User objects.
 */
@Singleton
public class UserRepository {

    private RateLimiter<String> rateLimiter = new RateLimiter<>(5, TimeUnit.SECONDS);

    private AppExecutors appExecutors;
    private UserDao userDao;
    private WorkerDao workerDao;
    private NanopoolService nanopoolService;

    @Inject
    public UserRepository(AppExecutors appExecutors, UserDao userDao, WorkerDao workerDao, NanopoolService nanopoolService) {
        this.appExecutors = appExecutors;
        this.userDao = userDao;
        this.workerDao = workerDao;
        this.nanopoolService = nanopoolService;
    }

    public LiveData<Resource<User>> loadUser(String address) {
        return new NetworkBoundResource<User, NanopoolResponse<User>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull NanopoolResponse<User> item) {
                User user = item.getData();
                if (user != null) {
                    userDao.insert(user);
                    insertUserWorkers(user);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return data == null || rateLimiter.shouldFetch(address);
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.findByAddress(address);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<NanopoolResponse<User>>> createCall() {
                return nanopoolService.getUser(address);
            }
        }.asLiveData();
    }

    @WorkerThread
    private void insertUserWorkers(User user) {
        List<Worker> workers = user.getWorkers();
        for (Worker worker : workers) {
            worker.setUid(user.getAccount());
        }
        List<Worker> existing = workerDao.getByAddress(user.getAccount());
        existing.removeAll(workers);
        if (!existing.isEmpty()) {
            workerDao.deleteAll(existing.toArray(new Worker[existing.size()]));
        }
        workerDao.insertAll(workers.toArray(new Worker[workers.size()]));
    }
}
