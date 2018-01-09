
package com.gaelanbolger.nanopoolmonitor.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "payment", primaryKeys = {"address", "txHash"})
public class Payment {

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
    @SerializedName("address")
    @NonNull
    private String address;
    @SerializedName("txHash")
    @NonNull
    private String txHash;
    @SerializedName("date")
    private long date;
    @SerializedName("amount")
    private double amount;
    @SerializedName("confirmed")
    private boolean confirmed;

    @Ignore
    public Payment() {
        this("", "", 0, 0, false);
    }

    public Payment(@NonNull String address, @NonNull String txHash, long date, double amount, boolean confirmed) {
        this.address = address;
        this.txHash = txHash;
        this.date = date;
        this.amount = amount;
        this.confirmed = confirmed;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(@NonNull String txHash) {
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

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(address, payment.address)
                && Objects.equals(txHash, payment.txHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, txHash);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "address='" + address + '\'' +
                ", txHash='" + txHash + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", confirmed=" + confirmed +
                '}';
    }
}
