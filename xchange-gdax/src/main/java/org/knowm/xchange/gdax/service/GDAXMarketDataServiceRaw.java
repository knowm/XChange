package org.knowm.xchange.gdax.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gdax.GDAX;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProduct;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductBook;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class GDAXMarketDataServiceRaw extends GDAXBaseService<GDAX> {

  public GDAXMarketDataServiceRaw(Exchange exchange) {

    super(GDAX.class, exchange);
  }

  public GDAXProductTicker getCoinbaseExProductTicker(CurrencyPair currencyPair) throws IOException {

    if (!this.checkProductExists(currencyPair)) {
      return null;
    }

    GDAXProductTicker tickerReturn = this.coinbaseEx.getProductTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
    return tickerReturn;
  }

  public GDAXProductStats getCoinbaseExProductStats(CurrencyPair currencyPair) throws IOException {

    if (!this.checkProductExists(currencyPair)) {
      return null;
    }

    GDAXProductStats statsReturn = this.coinbaseEx.getProductStats(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
    return statsReturn;
  }

  public GDAXProductBook getCoinbaseExProductOrderBook(CurrencyPair currencyPair) throws IOException {

    if (!this.checkProductExists(currencyPair)) {
      return null;
    }

    GDAXProductBook book = this.coinbaseEx.getProductOrderBook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), "1");
    return book;
  }

  public GDAXProductBook getCoinbaseExProductOrderBook(CurrencyPair currencyPair, int level) throws IOException {
    GDAXProductBook book = this.coinbaseEx.getProductOrderBook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(),
        String.valueOf(level));
    return book;
  }

  public GDAXTrade[] getCoinbaseExTrades(CurrencyPair currencyPair) throws IOException {

    return this.coinbaseEx.getTrades(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
  }

  public boolean checkProductExists(CurrencyPair currencyPair) {

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

  public List<GDAXProduct> getCoinbaseExProducts() throws IOException {

    return coinbaseEx.getProducts();
  }
}
