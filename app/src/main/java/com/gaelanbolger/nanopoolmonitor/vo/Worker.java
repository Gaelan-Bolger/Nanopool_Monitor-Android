
package com.gaelanbolger.nanopoolmonitor.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "worker", primaryKeys = {"address", "id", "uid"})
public class Worker {

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
    @SerializedName("address")
    @NonNull
    private String address;
    @SerializedName("id")
    @NonNull
    private String id;
    @SerializedName("uid")
    @NonNull
    private String uid;
    @SerializedName("hashrate")
    private String hashrate;
    @SerializedName("lastshare")
    private long lastshare;
    @SerializedName("rating")
    private long rating;

    @Ignore
    public Worker() {
        this("", "", "", null, 0, 0);
    }

    public Worker(@NonNull String address, @NonNull String id, @NonNull String uid, String hashrate, long lastshare, long rating) {
        this.address = address;
        this.id = id;
        this.uid = uid;
        this.hashrate = hashrate;
        this.lastshare = lastshare;
        this.rating = rating;
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

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
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
                "uid='" + uid + '\'' +
                ", hashrate='" + hashrate + '\'' +
                ", lastshare=" + lastshare +
                ", rating=" + rating +
                '}';
    }
}
