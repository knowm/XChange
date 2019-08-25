package org.knowm.xchange.lgo.service;

import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class LgoMarketDataService extends LgoMarketDataServiceRaw implements MarketDataService {

  public LgoMarketDataService(LgoExchange exchange) {
    super(exchange);
  }
}
