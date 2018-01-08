package com.gaelanbolger.nanopoolmonitor.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NanopoolResponse<T> {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private T data;
    @SerializedName("error")
    @Expose
    private String error;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
