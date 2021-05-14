package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.currency.CurrencyPair;

public class BTCMarketsMarketDataServiceRaw extends BTCMarketsBaseService {

  public BTCMarketsMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BTCMarketsTicker getBTCMarketsTicker(CurrencyPair currencyPair) throws IOException {
    return btcmPublic.getTicker(
        currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }

  public BTCMarketsOrderBook getBTCMarketsOrderBook(CurrencyPair currencyPair) throws IOException {
    return btcmPublic.getOrderBook(
        currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }
}
