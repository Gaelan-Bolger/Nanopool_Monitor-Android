package com.gaelanbolger.nanopoolmonitor.api;

import android.arch.lifecycle.LiveData;

import com.gaelanbolger.nanopoolmonitor.vo.AccountExist;
import com.gaelanbolger.nanopoolmonitor.vo.Balance;
import com.gaelanbolger.nanopoolmonitor.vo.UserResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NanopoolService {

    @GET("v1/eth/accountexist/{address}")
    LiveData<ApiResponse<AccountExist>> checkUser(@Path("address") String address);

    @GET("v1/eth/user/{address}")
    LiveData<ApiResponse<UserResponse>> getUser(@Path("address") String address);

    @GET("v1/eth/balance/{address}")
    LiveData<ApiResponse<Balance>> getBalance(@Path("address") String address);
}
