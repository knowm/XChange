package org.knowm.xchange.mexc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexc.MEXCExchange;
import org.knowm.xchange.service.trade.TradeService;

public class MEXCTradeService extends MEXCTradeServiceRaw implements TradeService {

  public MEXCTradeService(Exchange exchange) {
    super(exchange);
  }
}
