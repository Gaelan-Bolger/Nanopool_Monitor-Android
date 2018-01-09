package com.gaelanbolger.nanopoolmonitor.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gaelanbolger.nanopoolmonitor.AppExecutors;
import com.gaelanbolger.nanopoolmonitor.api.ApiResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolService;
import com.gaelanbolger.nanopoolmonitor.db.PaymentDao;
import com.gaelanbolger.nanopoolmonitor.util.RateLimiter;
import com.gaelanbolger.nanopoolmonitor.vo.Payment;
import com.gaelanbolger.nanopoolmonitor.vo.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class PaymentRepository {

    private RateLimiter<String> rateLimiter = new RateLimiter<>(5, TimeUnit.SECONDS);

    private AppExecutors appExecutors;
    private PaymentDao paymentDao;
    private NanopoolService nanopoolService;

    @Inject
    public PaymentRepository(AppExecutors appExecutors, PaymentDao paymentDao, NanopoolService nanopoolService) {
        this.appExecutors = appExecutors;
        this.paymentDao = paymentDao;
        this.nanopoolService = nanopoolService;
    }

    public LiveData<Resource<List<Payment>>> findByAddress(String address) {
        return new NetworkBoundResource<List<Payment>, NanopoolResponse<List<Payment>>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull NanopoolResponse<List<Payment>> item) {
                List<Payment> payments = item.getData();
                if (payments != null && payments.size() > 0) {
                    for (Payment payment : payments) {
                        payment.setAddress(address);
                    }
                    insertAll(payments.toArray(new Payment[payments.size()]));
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Payment> data) {
                return data == null || data.size() == 0 || rateLimiter.shouldFetch(address);
            }

            @NonNull
            @Override
            protected LiveData<List<Payment>> loadFromDb() {
                return paymentDao.findByAddress(address);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<NanopoolResponse<List<Payment>>>> createCall() {
                return nanopoolService.getPayments(address);
            }
        }.asLiveData();
    }

    private void insertAll(Payment... payments) {
        appExecutors.diskIO().execute(() -> paymentDao.insertAll(payments));
    }

    private void deleteAll(Payment... payments) {
        appExecutors.diskIO().execute(() -> paymentDao.deleteAll(payments));
    }

    public void deleteAll(String address) {
        appExecutors.diskIO().execute(() -> paymentDao.deleteAll(address));
    }
}
