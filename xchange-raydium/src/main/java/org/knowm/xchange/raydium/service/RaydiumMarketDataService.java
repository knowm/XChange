package org.knowm.xchange.raydium.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class RaydiumMarketDataService extends RaydiumMarketDataServiceRaw
    implements MarketDataService {

  public RaydiumMarketDataService(Exchange exchange) {
    super(exchange);
  }
}
