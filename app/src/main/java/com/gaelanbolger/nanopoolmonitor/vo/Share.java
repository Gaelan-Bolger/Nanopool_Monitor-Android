
package com.gaelanbolger.nanopoolmonitor.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "share", primaryKeys = {"address", "date"})
public class Share {

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
    @SerializedName("address")
    @NonNull
    private String address;
    @SerializedName("date")
    private long date;
    @SerializedName("shares")
    private int shares;

    @Ignore
    public Share() {
        this("", 0, 0);
    }

    public Share(@NonNull String address, long date, int shares) {
        this.address = address;
        this.date = date;
        this.shares = shares;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Share)) return false;
        Share share = (Share) o;
        return Objects.equals(address, share.address)
                && Objects.equals(date, share.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, date);
    }

    @Override
    public String toString() {
        return "Share{" +
                "address='" + address + '\'' +
                ", date=" + date +
                ", shares=" + shares +
                '}';
    }
}
