package org.knowm.xchange.dto.account;

import org.knowm.xchange.currency.CurrencyPair;

import javax.annotation.Nullable;

public final class AccountLeverageSetting {
    private final int leverage;
    @Nullable
    private final CurrencyPair pair;

    private AccountLeverageSetting(int leverage, CurrencyPair pair) {
        this.leverage = leverage;
        this.pair = pair;
    }

    public int getLeverage() {
        return leverage;
    }

    public CurrencyPair getPair() {
        return pair;
    }

    @Override
    public String toString() {
        return "AccountLeverageSetting{" +
                "leverage=" + leverage +
                ", pair=" + pair +
                '}';
    }

    public static class Builder {
        private int leverage;
        private CurrencyPair pair;

        public Builder leverage(int leverage) {
            this.leverage = leverage;
            return this;
        }

        public Builder pair(CurrencyPair pair) {
            this.pair = pair;
            return this;
        }

        public AccountLeverageSetting build() {
            return new AccountLeverageSetting(leverage, pair);
        }
    }
}
