package org.knowm.xchange.lgo.service;

import java.io.IOException;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.lgo.dto.currency.LgoCurrencies;
import org.knowm.xchange.lgo.dto.product.LgoProducts;

public class LgoMarketDataServiceRaw extends LgoBaseService {

  public LgoMarketDataServiceRaw(LgoExchange exchange) {
    super(exchange);
  }

  public LgoProducts getProducts() throws IOException {
    return this.proxy.getProducts(
        exchange.getNonceFactory().createValue(), exchange.getSignatureService());
  }

  public LgoCurrencies getCurrencies() throws IOException {
    return this.proxy.getCurrencies(
        exchange.getNonceFactory().createValue(), exchange.getSignatureService());
  }
}
