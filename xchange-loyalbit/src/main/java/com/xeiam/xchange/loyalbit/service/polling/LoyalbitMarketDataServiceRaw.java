package com.xeiam.xchange.loyalbit.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.loyalbit.Loyalbit;
import com.xeiam.xchange.loyalbit.dto.marketdata.LoyalbitOrderBook;

public class LoyalbitMarketDataServiceRaw extends LoyalbitBasePollingService {

  private final Loyalbit loyalbit;

  public LoyalbitMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.loyalbit = RestProxyFactory.createProxy(Loyalbit.class, exchange.getExchangeSpecification().getSslUri());
  }

  public LoyalbitOrderBook getLoyalbitOrderBook() throws IOException {
    return loyalbit.getOrderBook();
  }
}
