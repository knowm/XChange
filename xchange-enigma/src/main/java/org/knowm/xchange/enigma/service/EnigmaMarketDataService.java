package org.knowm.xchange.enigma.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class EnigmaMarketDataService extends EnigmaMarketDataServiceRaw
    implements MarketDataService {

  public EnigmaMarketDataService(Exchange exchange) {
    super(exchange);
  }
}
