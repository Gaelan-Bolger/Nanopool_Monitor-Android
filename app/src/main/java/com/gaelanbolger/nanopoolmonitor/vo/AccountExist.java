
package com.gaelanbolger.nanopoolmonitor.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountExist {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("error")
    @Expose
    private String error;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "AccountExist{" +
                "status=" + status +
                ", data='" + data + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
