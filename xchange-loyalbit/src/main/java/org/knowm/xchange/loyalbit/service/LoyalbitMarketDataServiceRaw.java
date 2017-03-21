package org.knowm.xchange.loyalbit.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.loyalbit.Loyalbit;
import org.knowm.xchange.loyalbit.dto.marketdata.LoyalbitOrderBook;
import org.knowm.xchange.loyalbit.dto.marketdata.LoyalbitTicker;

import si.mazi.rescu.RestProxyFactory;

public class LoyalbitMarketDataServiceRaw extends LoyalbitBaseService {

  private final Loyalbit loyalbit;

  public LoyalbitMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.loyalbit = RestProxyFactory.createProxy(Loyalbit.class, exchange.getExchangeSpecification().getSslUri());
  }

  public LoyalbitOrderBook getLoyalbitOrderBook() throws IOException {
    return loyalbit.getOrderBook();
  }

  public LoyalbitTicker getLoyalbitTicker() throws IOException {
    return loyalbit.getTicker();
  }
}
