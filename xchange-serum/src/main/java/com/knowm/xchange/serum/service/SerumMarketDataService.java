package com.knowm.xchange.serum.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class SerumMarketDataService extends SerumMarketDataServiceRaw implements MarketDataService {

  public SerumMarketDataService(Exchange exchange) {
    super(exchange);
  }
}
