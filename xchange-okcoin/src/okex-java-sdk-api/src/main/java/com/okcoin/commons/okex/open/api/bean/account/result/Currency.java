package com.okcoin.commons.okex.open.api.bean.account.result;

import java.math.BigDecimal;

public class Currency {
    private String currency;

    private String name;

    private Integer can_withdraw;

    private Integer can_deposit;

    private BigDecimal min_withdrawal;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCan_withdraw() {
        return can_withdraw;
    }

    public void setCan_withdraw(Integer can_withdraw) {
        this.can_withdraw = can_withdraw;
    }

    public Integer getCan_deposit() {
        return can_deposit;
    }

    public void setCan_deposit(Integer can_deposit) {
        this.can_deposit = can_deposit;
    }

    public BigDecimal getMin_withdrawal() {
        return min_withdrawal;
    }

    public void setMin_withdrawal(BigDecimal min_withdrawal) {
        this.min_withdrawal = min_withdrawal;
    }
}
