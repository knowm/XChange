package org.knowm.xchange.lgo.service;

import org.knowm.xchange.lgo.LgoErrorAdapter;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.lgo.dto.currency.LgoCurrencies;
import org.knowm.xchange.lgo.dto.product.LgoProducts;
import si.mazi.rescu.HttpStatusIOException;

public class LgoMarketDataServiceRaw extends LgoBaseService {

  public LgoMarketDataServiceRaw(LgoExchange exchange) {
    super(exchange);
  }

  public LgoProducts getProducts() {
    LgoProducts products = null;
    try {
      products =
          this.proxy.getProducts(
              exchange.getNonceFactory().createValue(), exchange.getSignatureService());
    } catch (HttpStatusIOException e) {
      LgoErrorAdapter.adapt(e);
    }
    return products;
  }

  public LgoCurrencies getCurrencies() {
    LgoCurrencies currencies = null;
    try {
      currencies =
          this.proxy.getCurrencies(
              exchange.getNonceFactory().createValue(), exchange.getSignatureService());
    } catch (HttpStatusIOException e) {
      LgoErrorAdapter.adapt(e);
    }
    return currencies;
  }
}
