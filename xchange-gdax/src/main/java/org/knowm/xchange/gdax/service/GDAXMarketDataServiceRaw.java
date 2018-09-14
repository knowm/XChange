package org.knowm.xchange.gdax.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gdax.dto.GDAXException;
import org.knowm.xchange.gdax.dto.GDAXTrades;
import org.knowm.xchange.gdax.dto.marketdata.GDAXCandle;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProduct;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductBook;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;

/** Created by Yingzhe on 4/6/2015. */
@Slf4j
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

  static Instant lastCall = null;
  static boolean rateLimited = false;

  public GDAXTrades getGDAXTradesExtended(CurrencyPair currencyPair, Long after, Integer limit)
      throws IOException {
    for (; ; ) {
      try {
        if (rateLimited) {
          long delta = Duration.between(lastCall, Instant.now()).toMillis();
          log.debug("Last call {} ms ago", delta);

          if (delta < 333) {
            try {
              log.debug("Sleeping for {}ms", 333 - delta);
              Thread.sleep(333 - delta);

            } catch (InterruptedException e) {
              log.debug("Unexpected exception in getGDAXTradesExtended", e);
            }
          } else if (delta > 3000) {
            log.debug("Clearing rate limiter");
            rateLimited = false;
          }
        }

        lastCall = Instant.now();

        GDAXTrades gdaxTrades =
            this.gdax.getTradesPageable(
                currencyPair.base.getCurrencyCode(),
                currencyPair.counter.getCurrencyCode(),
                after,
                limit);

        log.debug(
            "GDAXTrades: earliest={}, latest={}, {}",
            gdaxTrades.getEarliestTradeId(),
            gdaxTrades.getLatestTradeId(),
            gdaxTrades);

        return gdaxTrades;

      } catch (GDAXException e) {
        if (e.getHttpStatusCode() == 429) {
          log.debug("Rate limit exceeded, sleeping for 1000ms");

          rateLimited = true;

          try {
            Thread.sleep(1000);
          } catch (InterruptedException e1) {
            log.debug("Unexpected exception in getGDAXTradesExtended", e1);
          }

          log.debug("Retrying");

        } else {
          log.debug("Uncaught exception in getGDAXTradesExtended", e);

          throw handleError(e);
        }
      }
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
