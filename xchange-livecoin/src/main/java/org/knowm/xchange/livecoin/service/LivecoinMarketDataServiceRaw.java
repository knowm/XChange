package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestriction;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestrictions;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;

public class LivecoinMarketDataServiceRaw extends LivecoinBaseService<Livecoin> {

  public LivecoinMarketDataServiceRaw(Exchange exchange) {
    super(Livecoin.class, exchange);
  }

  public List<LivecoinRestriction> getConbaseExProducts() throws IOException {
    LivecoinRestrictions data = (LivecoinRestrictions) coinbaseEx.getProducts();
    return data.getRestrictions();
  }

  public LivecoinTicker getLivecoinTicker(CurrencyPair currencyPair) throws IOException {
    return this.coinbaseEx.getTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }
  
  public LivecoinOrderBook getOrderBookRaw(CurrencyPair currencyPair, int depth) throws IOException {
    if (!this.checkProductExists(currencyPair)) {
      return null;
    }

    return this.coinbaseEx.getOrderBook(currencyPair.base.getCurrencyCode().toUpperCase(), currencyPair.counter.getCurrencyCode().toUpperCase(),
        depth);
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
    return this.coinbaseEx.getTrades(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }
}
