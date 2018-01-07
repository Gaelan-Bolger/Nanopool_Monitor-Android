package com.gaelanbolger.nanopoolmonitor.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "account")
public class Account {

    @PrimaryKey
    @SerializedName("address")
    @NonNull
    private String address;

    @Ignore
    public Account() {
    }

    public Account(String address) {
        this.address = address;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }
}
