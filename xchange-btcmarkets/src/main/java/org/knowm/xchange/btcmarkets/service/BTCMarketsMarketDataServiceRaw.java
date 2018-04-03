package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarkets;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.RestProxyFactory;

public class BTCMarketsMarketDataServiceRaw extends BTCMarketsBaseService {

  private final BTCMarkets btcmarkets;

  public BTCMarketsMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.btcmarkets =
        RestProxyFactory.createProxy(
            BTCMarkets.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public BTCMarketsTicker getBTCMarketsTicker(CurrencyPair currencyPair) throws IOException {
    return btcmarkets.getTicker(
        currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }

  public BTCMarketsOrderBook getBTCMarketsOrderBook(CurrencyPair currencyPair) throws IOException {
    return btcmarkets.getOrderBook(
        currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }
}
