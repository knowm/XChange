package com.okcoin.commons.okex.open.api.bean.spot.result;

public class BorrowConfigDto {
    private String product_id;
    private String currency;
    private String available;
    private String rate;
    private String leverage_ratio;

    public String getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(final String product_id) {
        this.product_id = product_id;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getAvailable() {
        return this.available;
    }

    public void setAvailable(final String available) {
        this.available = available;
    }

    public String getRate() {
        return this.rate;
    }

    public void setRate(final String rate) {
        this.rate = rate;
    }

    public String getLeverage_ratio() {
        return this.leverage_ratio;
    }

    public void setLeverage_ratio(final String leverage_ratio) {
        this.leverage_ratio = leverage_ratio;
    }
}
