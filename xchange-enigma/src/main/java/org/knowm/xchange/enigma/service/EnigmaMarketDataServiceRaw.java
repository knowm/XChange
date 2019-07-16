package org.knowm.xchange.enigma.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProductMarketData;

import java.io.IOException;
import java.util.List;

public class EnigmaMarketDataServiceRaw extends EnigmaBaseService {

  public EnigmaMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public EnigmaProductMarketData getProductMarketData(int productId) throws IOException {
    return this.enigmaAuthenticated.getProductMarketData(accessToken(), productId);
  }

  public List<EnigmaProduct> getProducts() throws IOException {
    return this.enigmaAuthenticated.getProducts(accessToken());
  }
}
