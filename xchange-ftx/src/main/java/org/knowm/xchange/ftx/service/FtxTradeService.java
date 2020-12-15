package org.knowm.xchange.ftx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.trade.TradeService;

public class FtxTradeService extends FtxTradeServiceRaw implements TradeService {

    public FtxTradeService(Exchange exchange) {
        super(exchange);
    }
}
