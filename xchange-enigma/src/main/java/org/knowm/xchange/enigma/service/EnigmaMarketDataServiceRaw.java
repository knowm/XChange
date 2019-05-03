package org.knowm.xchange.enigma.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProductMarketData;

public class EnigmaMarketDataServiceRaw extends EnigmaBaseService {

  public EnigmaMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public EnigmaProductMarketData getProductMarketData(int productId) throws IOException {
    return this.enigmaAuthenticated.getProductMarketData(accessToken(), productId);
  }
}
