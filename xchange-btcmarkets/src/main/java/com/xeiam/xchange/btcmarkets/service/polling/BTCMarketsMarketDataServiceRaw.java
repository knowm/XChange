package com.xeiam.xchange.btcmarkets.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcmarkets.BTCMarkets;
import com.xeiam.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import com.xeiam.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import com.xeiam.xchange.currency.CurrencyPair;

public class BTCMarketsMarketDataServiceRaw extends BTCMarketsBasePollingService {

  private final BTCMarkets btcmarkets;

  public BTCMarketsMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.btcmarkets = RestProxyFactory.createProxy(BTCMarkets.class, exchange.getExchangeSpecification().getSslUri());
  }

  public BTCMarketsTicker getBTCMarketsTicker(CurrencyPair currencyPair) throws IOException {
    return btcmarkets.getTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }

  public BTCMarketsOrderBook getBTCMarketsOrderBook(CurrencyPair currencyPair) throws IOException {
    return btcmarkets.getOrderBook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }
}
