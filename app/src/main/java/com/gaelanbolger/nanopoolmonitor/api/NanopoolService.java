package com.gaelanbolger.nanopoolmonitor.api;

import android.arch.lifecycle.LiveData;

import com.gaelanbolger.nanopoolmonitor.vo.Payment;
import com.gaelanbolger.nanopoolmonitor.vo.Share;
import com.gaelanbolger.nanopoolmonitor.vo.User;
import com.gaelanbolger.nanopoolmonitor.vo.Worker;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NanopoolService {

    @GET("v1/eth/accountexist/{address}")
    LiveData<ApiResponse<NanopoolResponse<Boolean>>> accountExist(@Path("address") String address);

    @GET("v1/eth/user/{address}")
    LiveData<ApiResponse<NanopoolResponse<User>>> getUser(@Path("address") String address);

    @GET("v1/eth/balance/{address}")
    LiveData<ApiResponse<NanopoolResponse<Double>>> getBalance(@Path("address") String address);

    @GET("v1/eth/workers/{address}")
    LiveData<ApiResponse<NanopoolResponse<List<Worker>>>> getWorkers(@Path("address") String address);

    @GET("v1/eth/payments/{address}")
    LiveData<ApiResponse<NanopoolResponse<List<Payment>>>> getPayments(@Path("address") String address);

    @GET("v1/eth/shareratehistory/{address}")
    LiveData<ApiResponse<NanopoolResponse<List<Share>>>> getShares(@Path("address") String address);
}
