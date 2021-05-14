package org.knowm.xchange.coinbasepro.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTrades;
import org.knowm.xchange.coinbasepro.dto.marketdata.*;
import org.knowm.xchange.currency.CurrencyPair;

@Slf4j
public class CoinbaseProMarketDataServiceRaw extends CoinbaseProBaseService {

  public CoinbaseProMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CoinbaseProProductTicker getCoinbaseProProductTicker(CurrencyPair currencyPair)
      throws IOException {

    if (!checkProductExists(currencyPair)) {
      return null;
    }
    try {
      CoinbaseProProductTicker tickerReturn =
          coinbasePro.getProductTicker(
              currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
      return tickerReturn;
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProProductStats getCoinbaseProProductStats(CurrencyPair currencyPair)
      throws IOException {

    if (!checkProductExists(currencyPair)) {
      return null;
    }
    try {
      CoinbaseProProductStats statsReturn =
          coinbasePro.getProductStats(
              currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
      return statsReturn;
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProProductBook getCoinbaseProProductOrderBook(CurrencyPair currencyPair)
      throws IOException {

    if (!checkProductExists(currencyPair)) {
      return null;
    }

    try {
      CoinbaseProProductBook book =
          coinbasePro.getProductOrderBook(
              currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), "1");
      return book;
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProProductBook getCoinbaseProProductOrderBook(CurrencyPair currencyPair, int level)
      throws IOException {

    try {
      CoinbaseProProductBook book =
          coinbasePro.getProductOrderBook(
              currencyPair.base.getCurrencyCode(),
              currencyPair.counter.getCurrencyCode(),
              String.valueOf(level));
      return book;
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProTrade[] getCoinbaseProTrades(CurrencyPair currencyPair) throws IOException {

    try {
      return coinbasePro.getTrades(
          currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  static Instant lastCall = null;
  static boolean rateLimited = false;

  public CoinbaseProTrades getCoinbaseProTradesExtended(
      CurrencyPair currencyPair, Long after, Integer limit) throws IOException {

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
              log.debug("Unexpected exception in getCoinbaseProTradesExtended", e);
            }
          } else if (delta > 3000) {
            log.debug("Clearing rate limiter");
            rateLimited = false;
          }
        }
        lastCall = Instant.now();
        CoinbaseProTrades CoinbaseProTrades =
            coinbasePro.getTradesPageable(
                currencyPair.base.getCurrencyCode(),
                currencyPair.counter.getCurrencyCode(),
                after,
                limit);
        log.debug(
            "CoinbaseProTrades: earliest={}, latest={}, {}",
            CoinbaseProTrades.getEarliestTradeId(),
            CoinbaseProTrades.getLatestTradeId(),
            CoinbaseProTrades);
        return CoinbaseProTrades;
      } catch (CoinbaseProException e) {

        if (e.getHttpStatusCode() != 429) {
          throw handleError(e);
        }

        log.debug("Rate limit exceeded, sleeping for 1000ms");
        rateLimited = true;
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e1) {
          log.debug("Unexpected exception in getCoinbaseProTradesExtended", e1);
        }
        log.debug("Retrying");
      }
    }
  }

  public CoinbaseProCandle[] getCoinbaseProHistoricalCandles(
      CurrencyPair currencyPair, String start, String end, String granularity) throws IOException {

    try {
      return coinbasePro.getHistoricalCandles(
          currencyPair.base.getCurrencyCode(),
          currencyPair.counter.getCurrencyCode(),
          start,
          end,
          granularity);
    } catch (CoinbaseProException e) {
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

  public CoinbaseProProduct[] getCoinbaseProProducts() throws IOException {

    try {
      return coinbasePro.getProducts();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProCurrency[] getCoinbaseProCurrencies() throws IOException {

    try {
      return coinbasePro.getCurrencies();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }
}
