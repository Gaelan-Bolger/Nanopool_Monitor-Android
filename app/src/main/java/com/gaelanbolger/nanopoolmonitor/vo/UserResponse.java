package com.gaelanbolger.nanopoolmonitor.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private User data;
    @SerializedName("error")
    @Expose
    private String error;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getData() {
        return data;
    }

    public void setData(User user) {
        this.data = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "status=" + status +
                ", user=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
