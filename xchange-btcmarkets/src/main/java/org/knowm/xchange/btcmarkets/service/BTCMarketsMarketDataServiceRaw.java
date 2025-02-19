package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCMarketsMarketTradeParams;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCMarketsTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.params.Params;

public class BTCMarketsMarketDataServiceRaw extends BTCMarketsBaseService {

  public BTCMarketsMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BTCMarketsTicker getBTCMarketsTicker(CurrencyPair currencyPair) throws IOException {
    return btcmPublic.getTicker(
        currencyPair.getBase().getCurrencyCode(), currencyPair.getCounter().getCurrencyCode());
  }

  public BTCMarketsOrderBook getBTCMarketsOrderBook(CurrencyPair currencyPair) throws IOException {
    return btcmPublic.getOrderBook(
        currencyPair.getBase().getCurrencyCode(), currencyPair.getCounter().getCurrencyCode());
  }

  public List<BTCMarketsTrade> getBTCMarketsTrade(CurrencyPair currencyPair) throws IOException {
    return btcmPublic.getTrades(
        currencyPair.getBase().getCurrencyCode() + "-" + currencyPair.getCounter().getCurrencyCode());
  }

  public List<BTCMarketsTrade> getBTCMarketsTrade(CurrencyPair currencyPair, Params parameters)
      throws IOException {
    return btcmPublic.getTrades(
        ((BTCMarketsMarketTradeParams) parameters).before,
        ((BTCMarketsMarketTradeParams) parameters).after,
        ((BTCMarketsMarketTradeParams) parameters).limit,
        currencyPair.getBase().getCurrencyCode() + "-" + currencyPair.getCounter().getCurrencyCode());
  }
}
