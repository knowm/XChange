package org.knowm.xchange.coinbasepro.service;

import static org.knowm.xchange.coinbasepro.CoinbaseProResilience.PUBLIC_REST_ENDPOINT_RATE_LIMITER;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTrades;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCandle;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCurrency;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProduct;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductBook;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductStats;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductTicker;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProStats;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.InstrumentNotValidException;

public class CoinbaseProMarketDataServiceRaw extends CoinbaseProBaseService {

  public CoinbaseProMarketDataServiceRaw(
      CoinbaseProExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  /** https://docs.pro.coinbase.com/#get-product-ticker */
  public CoinbaseProProductTicker getCoinbaseProProductTicker(CurrencyPair currencyPair)
      throws IOException {

    if (!checkProductExists(currencyPair)) {
      throw new InstrumentNotValidException("Pair does not exist on CoinbasePro");
    }
    try {
      return decorateApiCall(
              () ->
                  coinbasePro.getProductTicker(
                      currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()))
          .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  /** https://docs.pro.coinbase.com/#get-24hr-stats */
  public CoinbaseProProductStats getCoinbaseProProductStats(CurrencyPair currencyPair)
      throws IOException {

    if (!checkProductExists(currencyPair)) {
      throw new InstrumentNotValidException("Pair does not exist on CoinbasePro");
    }
    try {
      return decorateApiCall(
              () ->
                  coinbasePro.getProductStats(
                      currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()))
          .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public Map<String, CoinbaseProStats> getCoinbaseProStats() throws IOException {
    try {
      return decorateApiCall(coinbasePro::getStats)
          .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  /** https://docs.pro.coinbase.com/#get-product-order-book */
  public CoinbaseProProductBook getCoinbaseProProductOrderBook(CurrencyPair currencyPair, int level)
      throws IOException {

    try {
      return decorateApiCall(
              () ->
                  coinbasePro.getProductOrderBook(
                      currencyPair.base.getCurrencyCode(),
                      currencyPair.counter.getCurrencyCode(),
                      String.valueOf(level)))
          .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  /** https://docs.pro.coinbase.com/#get-trades */
  public CoinbaseProTrade[] getCoinbaseProTrades(CurrencyPair currencyPair) throws IOException {
    try {
      return decorateApiCall(
              () ->
                  coinbasePro.getTrades(
                      currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()))
          .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  /** https://docs.pro.coinbase.com/#get-historic-rates */
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
          .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  /** https://docs.pro.coinbase.com/#get-products */
  public CoinbaseProProduct[] getCoinbaseProProducts() throws IOException {
    try {
      return decorateApiCall(coinbasePro::getProducts)
          .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  /** https://docs.pro.coinbase.com/#get-currencies */
  public CoinbaseProCurrency[] getCoinbaseProCurrencies() throws IOException {
    try {
      return decorateApiCall(coinbasePro::getCurrencies)
          .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  /** https://docs.pro.coinbase.com/#get-trades */
  public CoinbaseProTrades getCoinbaseProTradesExtended(
      CurrencyPair currencyPair, Long after, Integer limit) throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getTradesPageable(
                    currencyPair.base.getCurrencyCode(),
                    currencyPair.counter.getCurrencyCode(),
                    after,
                    limit))
        .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  public boolean checkProductExists(CurrencyPair currencyPair) {

    for (CurrencyPair cp : exchange.getExchangeSymbols()) {
      if (cp.base.getCurrencyCode().equalsIgnoreCase(currencyPair.base.getCurrencyCode())
          && cp.counter
              .getCurrencyCode()
              .equalsIgnoreCase(currencyPair.counter.getCurrencyCode())) {
        return true;
      }
    }
    return false;
  }
}
