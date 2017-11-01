package org.knowm.xchange.binance.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class BinanceCancelOrderParams implements CancelOrderParams {
    
    public final CurrencyPair pair;
    public final String orderId;

    public BinanceCancelOrderParams(CurrencyPair pair, String orderId) {
        this.pair = pair;
        this.orderId = orderId;
    }
}
