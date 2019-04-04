package com.okcoin.commons.okex.open.api.bean.ett.result;

import java.util.Objects;

/**
 * @author chuping.cui
 * @date 2018/7/4
 */
public class EttAccount {

    private String currency;
    private String balance;
    private String holds;
    private String available;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getHolds() {
        return holds;
    }

    public void setHolds(String holds) {
        this.holds = holds;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        EttAccount that = (EttAccount)o;
        return Objects.equals(currency, that.currency) &&
            Objects.equals(balance, that.balance) &&
            Objects.equals(holds, that.holds) &&
            Objects.equals(available, that.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, balance, holds, available);
    }

    @Override
    public String toString() {
        return "EttAccount{" +
            "currency='" + currency + '\'' +
            ", balance='" + balance + '\'' +
            ", holds='" + holds + '\'' +
            ", available='" + available + '\'' +
            '}';
    }
}
