package org.knowm.xchange.bybit.service;

import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BybitMarketDataService extends BybitMarketDataServiceRaw implements MarketDataService {
  public BybitMarketDataService(BybitExchange exchange) {
    super(exchange);
  }
}
