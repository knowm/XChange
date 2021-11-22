package org.knowm.xchange.binance.service.account.params;

import org.knowm.xchange.binance.dto.account.BinanceMarginType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.account.params.AccountMarginParams;

public class BinanceAccountMarginParams implements AccountMarginParams {
    private final BinanceMarginType marginType;
    private final CurrencyPair pair;

    public BinanceAccountMarginParams(BinanceMarginType marginType, CurrencyPair pair) {
        this.marginType = marginType;
        this.pair = pair;
    }

    public CurrencyPair getPair() {
        return pair;
    }

    public BinanceMarginType getMarginType() {
        return marginType;
    }

    @Override
    public String toString() {
        return "BinanceAccountMarginParams{" +
                "pair=" + pair +
                ", marginType=" + marginType +
                '}';
    }
}
