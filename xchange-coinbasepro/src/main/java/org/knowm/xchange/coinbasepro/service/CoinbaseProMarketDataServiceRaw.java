package org.knowm.xchange.coinbasepro.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCandle;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProduct;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductBook;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductStats;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductTicker;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProTrade;
import org.knowm.xchange.currency.CurrencyPair;

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
}
