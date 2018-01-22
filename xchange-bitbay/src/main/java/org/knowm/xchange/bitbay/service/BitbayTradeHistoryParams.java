package org.knowm.xchange.bitbay.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;

public class BitbayTradeHistoryParams implements TradeHistoryParamCurrencyPair {


    private CurrencyPair currencyPair;

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }


}
