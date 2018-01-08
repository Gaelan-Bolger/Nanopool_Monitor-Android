
package com.gaelanbolger.nanopoolmonitor.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "user")
public class User {

    @PrimaryKey
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
    @SerializedName("account")
    @NonNull
    private String account;
    @SerializedName("unconfirmed_balance")
    private String unconfirmedBalance;
    @SerializedName("balance")
    private String balance;
    @SerializedName("hashrate")
    private String hashrate;
    @Embedded(prefix = "ave_")
    @SerializedName("avgHashrate")
    private AvgHashrate avgHashrate;
    @Ignore
    @SerializedName("workers")
    private List<Worker> workers = new ArrayList<>();

    @Ignore
    public User() {
        this("", null, null, null, null);
    }

    public User(@NonNull String account, String unconfirmedBalance, String balance, String hashrate, AvgHashrate avgHashrate) {
        this(account, unconfirmedBalance, balance, hashrate, avgHashrate, new ArrayList<>());
    }

    @Ignore
    public User(@NonNull String account, String unconfirmedBalance, String balance, String hashrate, AvgHashrate avgHashrate, List<Worker> workers) {
        this.account = account;
        this.unconfirmedBalance = unconfirmedBalance;
        this.balance = balance;
        this.hashrate = hashrate;
        this.avgHashrate = avgHashrate;
        this.workers = workers;
    }

    @NonNull
    public String getAccount() {
        return account;
    }

    public void setAccount(@NonNull String account) {
        this.account = account;
    }

    public String getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public void setUnconfirmedBalance(String unconfirmedBalance) {
        this.unconfirmedBalance = unconfirmedBalance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getHashrate() {
        return hashrate;
    }

    public void setHashrate(String hashrate) {
        this.hashrate = hashrate;
    }

    public AvgHashrate getAvgHashrate() {
        return avgHashrate;
    }

    public void setAvgHashrate(AvgHashrate avgHashrate) {
        this.avgHashrate = avgHashrate;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(account, user.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", unconfirmedBalance='" + unconfirmedBalance + '\'' +
                ", balance='" + balance + '\'' +
                ", hashrate='" + hashrate + '\'' +
                ", avgHashrate=" + avgHashrate +
                ", workers=" + workers +
                '}';
    }
}
