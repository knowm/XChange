package com.okcoin.commons.okex.open.api.bean.account.result;

import java.math.BigDecimal;

public class WithdrawFee {

      private BigDecimal min_fee;

      private BigDecimal max_fee;


      private String currency;

    public BigDecimal getMin_fee() {
        return min_fee;
    }

    public void setMin_fee(BigDecimal min_fee) {
        this.min_fee = min_fee;
    }

    public BigDecimal getMax_fee() {
        return max_fee;
    }

    public void setMax_fee(BigDecimal max_fee) {
        this.max_fee = max_fee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
