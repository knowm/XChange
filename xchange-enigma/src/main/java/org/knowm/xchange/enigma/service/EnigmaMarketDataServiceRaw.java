package org.knowm.xchange.enigma.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProductMarketData;

public class EnigmaMarketDataServiceRaw extends EnigmaBaseService {

  public EnigmaMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public EnigmaProductMarketData getProductMarketData(String accessToken, int productId) throws IOException {
    try {
      return this.enigmaAuthenticated.getProductMarketData(accessToken, productId);
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
  }
}
