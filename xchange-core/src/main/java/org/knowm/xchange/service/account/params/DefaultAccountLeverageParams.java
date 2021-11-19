package org.knowm.xchange.service.account.params;

public class DefaultAccountLeverageParams implements AccountLeverageParams {
    private final int leverage;

    public DefaultAccountLeverageParams(int leverage) {
        this.leverage = leverage;
    }

    @Override
    public int getLeverage() {
        return leverage;
    }

    @Override
    public String toString() {
        return "DefaultAccountLeverageParams{" +
                "leverage=" + leverage +
                '}';
    }
}

