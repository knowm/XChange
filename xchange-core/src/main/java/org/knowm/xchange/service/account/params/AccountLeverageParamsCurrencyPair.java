package org.knowm.xchange.service.account.params;

import org.knowm.xchange.currency.CurrencyPair;

public class AccountLeverageParamsCurrencyPair implements AccountLeverageParams {
    private final int leverage;
    private final CurrencyPair pair;

    public AccountLeverageParamsCurrencyPair(int leverage, CurrencyPair pair) {
        this.leverage = leverage;
        this.pair = pair;
    }

    @Override
    public int getLeverage() {
        return leverage;
    }

    public CurrencyPair getPair() {
        return pair;
    }

    @Override
    public String toString() {
        return "AccountLeverageParamsCurrencyPair{" +
                "leverage=" + leverage +
                ", pair=" + pair +
                '}';
    }
}
