
package com.gaelanbolger.nanopoolmonitor.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "payment")
public class Payment {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private long id;
    @SerializedName("txHash")
    private String txHash;
    @SerializedName("date")
    private long date;
    @SerializedName("amount")
    private double amount;
    @SerializedName("confirmed")
    private boolean confirmed;

    @Ignore
    public Payment() {
        this(0, "", 0, 0, false);
    }

    public Payment(long id, String txHash, long date, double amount, boolean confirmed) {
        this.id = id;
        this.txHash = txHash;
        this.date = date;
        this.amount = amount;
        this.confirmed = confirmed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment worker = (Payment) o;
        return Objects.equals(id, worker.id)
                && Objects.equals(txHash, worker.txHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, txHash);
    }


}
