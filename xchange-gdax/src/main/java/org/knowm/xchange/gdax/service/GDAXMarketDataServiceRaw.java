package org.knowm.xchange.gdax.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gdax.dto.GDAXException;
import org.knowm.xchange.gdax.dto.marketdata.GDAXCandle;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProduct;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductBook;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;

/** Created by Yingzhe on 4/6/2015. */
public class GDAXMarketDataServiceRaw extends GDAXBaseService {

  public GDAXMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public GDAXProductTicker getGDAXProductTicker(CurrencyPair currencyPair) throws IOException {

    if (!this.checkProductExists(currencyPair)) {
      return null;
    }
    try {
      GDAXProductTicker tickerReturn =
          this.gdax.getProductTicker(
              currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
      return tickerReturn;
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  public GDAXProductStats getGDAXProductStats(CurrencyPair currencyPair) throws IOException {

    if (!this.checkProductExists(currencyPair)) {
      return null;
    }
    try {
      GDAXProductStats statsReturn =
          this.gdax.getProductStats(
              currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
      return statsReturn;
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  public GDAXProductBook getGDAXProductOrderBook(CurrencyPair currencyPair) throws IOException {

    if (!this.checkProductExists(currencyPair)) {
      return null;
    }

    try {
      GDAXProductBook book =
          this.gdax.getProductOrderBook(
              currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), "1");
      return book;
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  public GDAXProductBook getGDAXProductOrderBook(CurrencyPair currencyPair, int level)
      throws IOException {

    try {
      GDAXProductBook book =
          this.gdax.getProductOrderBook(
              currencyPair.base.getCurrencyCode(),
              currencyPair.counter.getCurrencyCode(),
              String.valueOf(level));
      return book;
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  public GDAXTrade[] getGDAXTrades(CurrencyPair currencyPair) throws IOException {

    try {
      return this.gdax.getTrades(
          currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  public GDAXCandle[] getGDAXHistoricalCandles(
      CurrencyPair currencyPair, String start, String end, String granularity) throws IOException {

    try {
      return this.gdax.getHistoricalCandles(
          currencyPair.base.getCurrencyCode(),
          currencyPair.counter.getCurrencyCode(),
          start,
          end,
          granularity);
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  public boolean checkProductExists(CurrencyPair currencyPair) {

    boolean currencyPairSupported = false;
    for (CurrencyPair cp : exchange.getExchangeSymbols()) {
      if (cp.base.getCurrencyCode().equalsIgnoreCase(currencyPair.base.getCurrencyCode())
          && cp.counter
              .getCurrencyCode()
              .equalsIgnoreCase(currencyPair.counter.getCurrencyCode())) {
        currencyPairSupported = true;
        break;
      }
    }

    return currencyPairSupported;
  }

  public GDAXProduct[] getGDAXProducts() throws IOException {

    try {
      return gdax.getProducts();
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }
}
