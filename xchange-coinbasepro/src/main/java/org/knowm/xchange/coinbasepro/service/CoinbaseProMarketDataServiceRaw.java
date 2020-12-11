package org.knowm.xchange.coinbasepro.service;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTrades;
import org.knowm.xchange.coinbasepro.dto.marketdata.*;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;

import static org.knowm.xchange.coinbasepro.CoinbaseProResilience.PUBLIC_PER_SECOND_RATE_LIMITER;

@Slf4j
public class CoinbaseProMarketDataServiceRaw extends CoinbaseProBaseService {
  private final String ORDER_BOOK_LEVEL_ONE = "1";

  public CoinbaseProMarketDataServiceRaw(
      CoinbaseProExchange exchange, ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
  }

  public CoinbaseProProductTicker getCoinbaseProProductTicker(CurrencyPair currencyPair)
      throws IOException {

    if (!checkProductExists(currencyPair)) {
      return null;
    }
    try {
      CoinbaseProProductTicker tickerReturn =
          decorateApiCall(
                  () ->
                      coinbasePro.getProductTicker(
                          currencyPair.base.getCurrencyCode(),
                          currencyPair.counter.getCurrencyCode()))
              .withRetry(retry("tickerReturn"))
              .withRateLimiter(rateLimiter(PUBLIC_PER_SECOND_RATE_LIMITER))
              .call();
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
          decorateApiCall(
                  () ->
                      coinbasePro.getProductStats(
                          currencyPair.base.getCurrencyCode(),
                          currencyPair.counter.getCurrencyCode()))
              .withRetry(retry("tickerReturn"))
              .withRateLimiter(rateLimiter(PUBLIC_PER_SECOND_RATE_LIMITER))
              .call();
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
          decorateApiCall(
                  () ->
                      coinbasePro.getProductOrderBook(
                          currencyPair.base.getCurrencyCode(),
                          currencyPair.counter.getCurrencyCode(),
                          ORDER_BOOK_LEVEL_ONE))
              .withRetry(retry("getCoinbaseProProductOrderBook"))
              .withRateLimiter(rateLimiter(PUBLIC_PER_SECOND_RATE_LIMITER))
              .call();
      return book;
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProProductBook getCoinbaseProProductOrderBook(CurrencyPair currencyPair, int level)
      throws IOException {

    try {
      CoinbaseProProductBook book =
          decorateApiCall(
                  () ->
                      coinbasePro.getProductOrderBook(
                          currencyPair.base.getCurrencyCode(),
                          currencyPair.counter.getCurrencyCode(),
                          String.valueOf(level)))
              .withRetry(retry("getCoinbaseProProductOrderBook"))
              .withRateLimiter(rateLimiter(PUBLIC_PER_SECOND_RATE_LIMITER))
              .call();
      return book;
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProTrade[] getCoinbaseProTrades(CurrencyPair currencyPair) throws IOException {

    try {
      return decorateApiCall(
              () ->
                  coinbasePro.getTrades(
                      currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()))
          .withRetry(retry("getCoinbaseProTrades"))
          .withRateLimiter(rateLimiter(PUBLIC_PER_SECOND_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProTrades getCoinbaseProTradesExtended(
      CurrencyPair currencyPair, Long after, Integer limit) throws IOException {
    try {
      return decorateApiCall(
              () ->
                  coinbasePro.getTradesPageable(
                      currencyPair.base.getCurrencyCode(),
                      currencyPair.counter.getCurrencyCode(),
                      after,
                      limit))
          .withRetry(retry("getCoinbaseProTradesExtended"))
          .withRateLimiter(rateLimiter(PUBLIC_PER_SECOND_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProCandle[] getCoinbaseProHistoricalCandles(
      CurrencyPair currencyPair, String start, String end, String granularity) throws IOException {

    try {
      return decorateApiCall(
              () ->
                  coinbasePro.getHistoricalCandles(
                      currencyPair.base.getCurrencyCode(),
                      currencyPair.counter.getCurrencyCode(),
                      start,
                      end,
                      granularity))
          .withRetry(retry("getCoinbaseProHistoricalCandles"))
          .withRateLimiter(rateLimiter(PUBLIC_PER_SECOND_RATE_LIMITER))
          .call();
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
      return decorateApiCall(() -> coinbasePro.getProducts())
          .withRetry(retry("getCoinbaseProProducts"))
          .withRateLimiter(rateLimiter(PUBLIC_PER_SECOND_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProCurrency[] getCoinbaseProCurrencies() throws IOException {

    try {
      return decorateApiCall(() -> coinbasePro.getCurrencies())
          .withRetry(retry("getCoinbaseProCurrencies"))
          .withRateLimiter(rateLimiter(PUBLIC_PER_SECOND_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }
}
