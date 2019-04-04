package com.okcoin.commons.okex.open.api.bean.ett.result;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttConstituents {

    private String currency;
    private BigDecimal amount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        EttConstituents that = (EttConstituents)o;
        return Objects.equals(currency, that.currency) &&
            Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return "EttConstituents{" +
            "currency='" + currency + '\'' +
            ", amount=" + amount +
            '}';
    }
}
