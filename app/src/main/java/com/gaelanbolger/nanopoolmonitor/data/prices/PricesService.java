package com.gaelanbolger.nanopoolmonitor.data.prices;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.gaelanbolger.nanopoolmonitor.api.NanopoolResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolService;
import com.gaelanbolger.nanopoolmonitor.vo.Prices;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class PricesService extends JobIntentService {

    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    NanopoolService nanopoolService;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        nanopoolService.getPrices().enqueue(new Callback<NanopoolResponse<Prices>>() {
            @Override
            public void onResponse(@NonNull Call<NanopoolResponse<Prices>> call, @NonNull Response<NanopoolResponse<Prices>> response) {
                NanopoolResponse<Prices> body;
                if (response.isSuccessful() && (body = response.body()) != null) {
                    Prices prices = body.getData();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat("price_usd", (float) prices.getPriceUsd());
                    editor.putFloat("price_btc", (float) prices.getPriceBtc());
                    editor.apply();
                    Timber.e("onResponse: Prices updated");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NanopoolResponse<Prices>> call, @NonNull Throwable t) {
                Timber.e("onFailure: Error fetching prices", t);
            }
        });
    }
}
