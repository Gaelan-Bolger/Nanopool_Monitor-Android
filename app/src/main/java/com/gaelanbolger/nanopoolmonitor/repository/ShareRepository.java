package com.gaelanbolger.nanopoolmonitor.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gaelanbolger.nanopoolmonitor.AppExecutors;
import com.gaelanbolger.nanopoolmonitor.api.ApiResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolService;
import com.gaelanbolger.nanopoolmonitor.db.ShareDao;
import com.gaelanbolger.nanopoolmonitor.util.RateLimiter;
import com.gaelanbolger.nanopoolmonitor.vo.Resource;
import com.gaelanbolger.nanopoolmonitor.vo.Share;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository that handles Share objects.
 */
@Singleton
public class ShareRepository {

    private RateLimiter<String> rateLimiter = new RateLimiter<>(5, TimeUnit.SECONDS);

    private AppExecutors appExecutors;
    private ShareDao shareDao;
    private NanopoolService nanopoolService;

    @Inject
    public ShareRepository(AppExecutors appExecutors, ShareDao shareDao, NanopoolService nanopoolService) {
        this.appExecutors = appExecutors;
        this.shareDao = shareDao;
        this.nanopoolService = nanopoolService;
    }

    public LiveData<Resource<List<Share>>> findByAddress(String address) {
        return new NetworkBoundResource<List<Share>, NanopoolResponse<List<Share>>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull NanopoolResponse<List<Share>> item) {
                List<Share> shares = item.getData();
                if (shares != null && shares.size() > 0) {
                    for (Share share : shares) {
                        share.setAddress(address);
                    }
                    shareDao.insertAll(shares.toArray(new Share[shares.size()]));
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Share> data) {
                return data == null || data.size() == 0 || rateLimiter.shouldFetch(address);
            }

            @NonNull
            @Override
            protected LiveData<List<Share>> loadFromDb() {
                return shareDao.findByAddress(address);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<NanopoolResponse<List<Share>>>> createCall() {
                return nanopoolService.getShares(address);
            }
        }.asLiveData();
    }
}
