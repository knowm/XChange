package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.currency.CurrencyPair;

public interface OrderQueryParamCurrencyPair extends OrderQueryParams {
    void setCurrencyPair(CurrencyPair currencyPair);
    CurrencyPair getCurrencyPair();
}
