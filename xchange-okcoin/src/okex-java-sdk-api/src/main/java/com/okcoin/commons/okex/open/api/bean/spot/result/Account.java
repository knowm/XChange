package com.okcoin.commons.okex.open.api.bean.spot.result;

public class Account {

    private String id;
    private String currency;
    private String balance;
    private String available;
    private String hold;

    public String getHold() {
        return this.hold;
    }

    public void setHold(final String hold) {
        this.hold = hold;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(final String balance) {
        this.balance = balance;
    }

    public String getAvailable() {
        return this.available;
    }

    public void setAvailable(final String available) {
        this.available = available;
    }

}
