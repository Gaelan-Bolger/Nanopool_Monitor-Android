package com.gaelanbolger.nanopoolmonitor.repository;

import android.arch.lifecycle.LiveData;

import com.gaelanbolger.nanopoolmonitor.AppExecutors;
import com.gaelanbolger.nanopoolmonitor.db.WorkerDao;
import com.gaelanbolger.nanopoolmonitor.vo.Worker;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository that handles Worker objects.
 */
@Singleton
public class WorkerRepository {

    private AppExecutors appExecutors;
    private WorkerDao workerDao;

    @Inject
    public WorkerRepository(AppExecutors appExecutors, WorkerDao workerDao) {
        this.appExecutors = appExecutors;
        this.workerDao = workerDao;
    }

    public LiveData<List<Worker>> findByAddress(String address) {
        return workerDao.findByAddress(address);
    }

    public void deleteAll(Worker... workers) {
        appExecutors.diskIO().execute(() -> workerDao.deleteAll(workers));
    }

    public void insertAll(Worker... workers) {
        appExecutors.diskIO().execute(() -> workerDao.insertAll(workers));
    }
}
