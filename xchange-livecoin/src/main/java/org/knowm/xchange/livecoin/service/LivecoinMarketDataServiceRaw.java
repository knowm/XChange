package org.knowm.xchange.livecoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestriction;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LivecoinMarketDataServiceRaw extends LivecoinBaseService<Livecoin> {

  public LivecoinMarketDataServiceRaw(Exchange exchange) {
    super(Livecoin.class, exchange);
  }

  public List<LivecoinRestriction> getRestrictions() throws IOException {
    return service.getRestrictions().getRestrictions();
  }

  public LivecoinTicker getLivecoinTicker(CurrencyPair currencyPair) throws IOException {
    return service.getTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }

  public List<LivecoinTicker> getAllTickers() throws IOException {
    return service.getTicker();
  }

  public LivecoinOrderBook getOrderBookRaw(CurrencyPair currencyPair, int depth) throws IOException {
    if (!this.checkProductExists(currencyPair)) {
      return null;
    }

    return service.getOrderBook(
        currencyPair.base.getCurrencyCode().toUpperCase(),
        currencyPair.counter.getCurrencyCode().toUpperCase(),
        depth
    );
  }

  public Map<CurrencyPair, LivecoinOrderBook> getAllOrderBooksRaw(int depth) throws IOException {
    return LivecoinAdapters.adaptToCurrencyPairKeysMap(service.getAllOrderBooks(depth));

  }

  public boolean checkProductExists(CurrencyPair currencyPair) throws IOException {
    boolean currencyPairSupported = false;
    for (CurrencyPair cp : exchange.getExchangeSymbols()) {
      if (cp.base.getCurrencyCode().equalsIgnoreCase(currencyPair.base.getCurrencyCode())
          && cp.counter.getCurrencyCode().equalsIgnoreCase(currencyPair.counter.getCurrencyCode())) {
        currencyPairSupported = true;
        break;
      }
    }

    return currencyPairSupported;
  }

  public LivecoinTrade[] getTrades(CurrencyPair currencyPair) throws IOException {
    return service.getTrades(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }
}
