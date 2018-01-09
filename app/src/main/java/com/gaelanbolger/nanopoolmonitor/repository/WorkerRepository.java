package com.gaelanbolger.nanopoolmonitor.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gaelanbolger.nanopoolmonitor.AppExecutors;
import com.gaelanbolger.nanopoolmonitor.api.ApiResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolService;
import com.gaelanbolger.nanopoolmonitor.db.WorkerDao;
import com.gaelanbolger.nanopoolmonitor.util.RateLimiter;
import com.gaelanbolger.nanopoolmonitor.vo.Resource;
import com.gaelanbolger.nanopoolmonitor.vo.Worker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository that handles Worker objects.
 */
@Singleton
public class WorkerRepository {

    private RateLimiter<String> rateLimiter = new RateLimiter<>(5, TimeUnit.SECONDS);

    private AppExecutors appExecutors;
    private WorkerDao workerDao;
    private NanopoolService nanopoolService;

    @Inject
    public WorkerRepository(AppExecutors appExecutors, WorkerDao workerDao, NanopoolService nanopoolService) {
        this.appExecutors = appExecutors;
        this.workerDao = workerDao;
        this.nanopoolService = nanopoolService;
    }

    public LiveData<Resource<List<Worker>>> findByAddress(String address) {
        return new NetworkBoundResource<List<Worker>, NanopoolResponse<List<Worker>>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull NanopoolResponse<List<Worker>> item) {
                List<Worker> workers = item.getData();
                if (workers != null && workers.size() > 0) {
                    for (Worker worker : workers) {
                        worker.setAddress(address);
                    }
                    insertAll(workers.toArray(new Worker[workers.size()]));
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Worker> data) {
                return data == null || data.size() == 0 || rateLimiter.shouldFetch(address);
            }

            @NonNull
            @Override
            protected LiveData<List<Worker>> loadFromDb() {
                return workerDao.findByAddress(address);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<NanopoolResponse<List<Worker>>>> createCall() {
                return nanopoolService.getWorkers(address);
            }
        }.asLiveData();
    }

    public void insertAll(Worker... workers) {
        appExecutors.diskIO().execute(() -> workerDao.insertAll(workers));
    }

    public void deleteAll(Worker... workers) {
        appExecutors.diskIO().execute(() -> workerDao.deleteAll(workers));
    }

    public void deleteAll(String address) {
        appExecutors.diskIO().execute(() -> workerDao.deleteAll(address));
    }
}
