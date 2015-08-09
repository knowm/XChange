package com.xeiam.xchange.loyalbit.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.loyalbit.Loyalbit;
import com.xeiam.xchange.loyalbit.dto.marketdata.LoyalbitOrderBook;
import com.xeiam.xchange.loyalbit.dto.marketdata.LoyalbitTicker;

import si.mazi.rescu.RestProxyFactory;

public class LoyalbitMarketDataServiceRaw extends LoyalbitBasePollingService {

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
