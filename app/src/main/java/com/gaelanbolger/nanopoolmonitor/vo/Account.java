package com.gaelanbolger.nanopoolmonitor.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "account")
public class Account {

    @PrimaryKey
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
    @SerializedName("address")
    @NonNull
    private String address;

    @Ignore
    public Account() {
        this("");
    }

    public Account(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(address, account.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "Account{" +
                "address='" + address + '\'' +
                '}';
    }
}
