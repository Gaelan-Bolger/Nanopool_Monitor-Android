
package com.gaelanbolger.nanopoolmonitor.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "worker")
public class Worker {

    @SerializedName("id")
    private String id;
    @PrimaryKey
    @SerializedName("uid")
    @NonNull
    private long uid;
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
    }

    public Worker(String id, long uid, String hashrate, long lastshare, long rating, String h1, String h3, String h6, String h12, String h24) {
        super();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
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

}
