package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestriction;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;

public class LivecoinMarketDataServiceRaw extends LivecoinBaseService<Livecoin> {

  public LivecoinMarketDataServiceRaw(Exchange exchange) {
    super(Livecoin.class, exchange);
  }

  public List<LivecoinRestriction> getRestrictions() throws IOException {
    return service.getRestrictions().getRestrictions();
  }

  public LivecoinTicker getTickerRaw(CurrencyPair currencyPair) throws IOException {
    return service.getTicker(
        currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }

  public List<LivecoinTicker> getTickersRaw() throws IOException {
    return service.getTicker();
  }

  public LivecoinOrderBook getOrderBookRaw(
      CurrencyPair currencyPair, int depth, Boolean groupByPrice) throws IOException {
    return service.getOrderBook(
        currencyPair.base.getCurrencyCode().toUpperCase(),
        currencyPair.counter.getCurrencyCode().toUpperCase(),
        depth,
        groupByPrice.toString());
  }

  public Map<CurrencyPair, LivecoinOrderBook> getAllOrderBooksRaw(int depth, Boolean groupByPrice)
      throws IOException {
    return LivecoinAdapters.adaptToCurrencyPairKeysMap(
        service.getAllOrderBooks(depth, groupByPrice.toString()));
  }

  public List<LivecoinTrade> getTradesRaw(CurrencyPair currencyPair) throws IOException {
    return service.getTrades(
        currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }
}
