package org.knowm.xchange.ftx.dto.trade;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;

public class FtxTriggerOpenOrdersParams implements OpenOrdersParamCurrencyPair {

    private CurrencyPair currencyPair;

    public FtxTriggerOpenOrdersParams(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {
        this.currencyPair = pair;
    }
}
