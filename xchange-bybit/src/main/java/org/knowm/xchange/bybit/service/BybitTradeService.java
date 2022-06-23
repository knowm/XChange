package org.knowm.xchange.bybit.service;

import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.service.trade.TradeService;

public class BybitTradeService extends BybitTradeServiceRaw implements TradeService {

    public BybitTradeService(BybitExchange exchange) {
        super(exchange);
    }
}
