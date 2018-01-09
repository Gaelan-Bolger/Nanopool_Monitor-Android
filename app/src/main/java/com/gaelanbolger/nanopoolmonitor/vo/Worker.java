
package com.gaelanbolger.nanopoolmonitor.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "worker", primaryKeys = {"address", "id"})
public class Worker {

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
    @SerializedName("address")
    @NonNull
    private String address;
    @SerializedName("id")
    @NonNull
    private String id;
    @SerializedName("uid")
    private String uid;
    @SerializedName("hashrate")
    private String hashrate;
    @SerializedName("lastshare")
    private long lastshare;
    @SerializedName("rating")
    private long rating;
    @SerializedName("h1")
    private String h1;
    @SerializedName("h3")
    private String h3;
    @SerializedName("h6")
    private String h6;
    @SerializedName("h12")
    private String h12;
    @SerializedName("h24")
    private String h24;

    @Ignore
    public Worker() {
        this("", "", "", null, 0, 0, null, null, null, null, null);
    }

    public Worker(@NonNull String address, @NonNull String id, String uid, String hashrate, long lastshare, long rating, String h1, String h3, String h6, String h12, String h24) {
        this.address = address;
        this.id = id;
        this.uid = uid;
        this.hashrate = hashrate;
        this.lastshare = lastshare;
        this.rating = rating;
        this.h1 = h1;
        this.h3 = h3;
        this.h6 = h6;
        this.h12 = h12;
        this.h24 = h24;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHashrate() {
        return hashrate;
    }

    public void setHashrate(String hashrate) {
        this.hashrate = hashrate;
    }

    public long getLastshare() {
        return lastshare;
    }

    public void setLastshare(long lastshare) {
        this.lastshare = lastshare;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getH3() {
        return h3;
    }

    public void setH3(String h3) {
        this.h3 = h3;
    }

    public String getH6() {
        return h6;
    }

    public void setH6(String h6) {
        this.h6 = h6;
    }

    public String getH12() {
        return h12;
    }

    public void setH12(String h12) {
        this.h12 = h12;
    }

    public String getH24() {
        return h24;
    }

    public void setH24(String h24) {
        this.h24 = h24;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        Worker worker = (Worker) o;
        return Objects.equals(address, worker.address) &&
                Objects.equals(id, worker.id) &&
                Objects.equals(uid, worker.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, id, uid);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "address='" + address + '\'' +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", hashrate='" + hashrate + '\'' +
                ", lastshare=" + lastshare +
                ", rating=" + rating +
                ", h1='" + h1 + '\'' +
                ", h3='" + h3 + '\'' +
                ", h6='" + h6 + '\'' +
                ", h12='" + h12 + '\'' +
                ", h24='" + h24 + '\'' +
                '}';
    }
}
