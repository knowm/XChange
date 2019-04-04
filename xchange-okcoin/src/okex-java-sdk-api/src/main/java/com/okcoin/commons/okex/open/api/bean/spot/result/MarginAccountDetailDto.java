package com.okcoin.commons.okex.open.api.bean.spot.result;

public class MarginAccountDetailDto {
    private String balance;
    private String available;
    private String holds;

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

    public String getHolds() {
        return this.holds;
    }

    public void setHolds(final String holds) {
        this.holds = holds;
    }
}
