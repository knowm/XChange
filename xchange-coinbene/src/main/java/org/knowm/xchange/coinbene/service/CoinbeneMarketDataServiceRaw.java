package org.knowm.xchange.coinbene.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbene.CoinbeneException;
import org.knowm.xchange.coinbene.dto.CoinbeneAdapters;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneOrderBook;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneTicker;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneTrades;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

public class CoinbeneMarketDataServiceRaw extends CoinbeneBaseService {

  protected CoinbeneMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoinbeneTicker.Container getCoinbeneTicker(CurrencyPair currencyPair) throws IOException {
    try {
      return checkSuccess(coinbene.ticker(CoinbeneAdapters.adaptCurrencyPair(currencyPair)));
    } catch (CoinbeneException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  public CoinbeneOrderBook.Container getCoinbeneOrderBook(CurrencyPair currencyPair)
      throws IOException {
    return getCoinbeneOrderBook(currencyPair, null);
  }

  public CoinbeneOrderBook.Container getCoinbeneOrderBook(CurrencyPair currencyPair, Integer size)
      throws IOException {
    try {
      return checkSuccess(
          coinbene.orderBook(CoinbeneAdapters.adaptCurrencyPair(currencyPair), size));
    } catch (CoinbeneException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  public CoinbeneTrades getCoinbeneTrades(CurrencyPair currencyPair) throws IOException {
    return getCoinbeneTrades(currencyPair, null);
  }

  public CoinbeneTrades getCoinbeneTrades(CurrencyPair currencyPair, Integer size)
      throws IOException {
    try {
      return checkSuccess(coinbene.trades(CoinbeneAdapters.adaptCurrencyPair(currencyPair), size));
    } catch (CoinbeneException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }
}
