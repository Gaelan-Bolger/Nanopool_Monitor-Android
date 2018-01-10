
package com.gaelanbolger.nanopoolmonitor.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prices {

    @SerializedName("price_btc")
    @Expose
    private double priceBtc;
    @SerializedName("price_usd")
    @Expose
    private double priceUsd;
    @SerializedName("price_eur")
    @Expose
    private double priceEur;
    @SerializedName("price_rur")
    @Expose
    private double priceRur;
    @SerializedName("price_cny")
    @Expose
    private double priceCny;

    public Prices() {
    }

    public Prices(double priceBtc, double priceUsd, double priceEur, double priceRur, double priceCny) {
        this.priceBtc = priceBtc;
        this.priceUsd = priceUsd;
        this.priceEur = priceEur;
        this.priceRur = priceRur;
        this.priceCny = priceCny;
    }

    public double getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(double priceBtc) {
        this.priceBtc = priceBtc;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public double getPriceEur() {
        return priceEur;
    }

    public void setPriceEur(double priceEur) {
        this.priceEur = priceEur;
    }

    public double getPriceRur() {
        return priceRur;
    }

    public void setPriceRur(double priceRur) {
        this.priceRur = priceRur;
    }

    public double getPriceCny() {
        return priceCny;
    }

    public void setPriceCny(double priceCny) {
        this.priceCny = priceCny;
    }
}
