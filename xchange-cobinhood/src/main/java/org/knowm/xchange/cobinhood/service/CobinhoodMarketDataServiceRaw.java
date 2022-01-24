package org.knowm.xchange.cobinhood.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cobinhood.CobinhoodAdapters;
import org.knowm.xchange.cobinhood.CobinhoodException;
import org.knowm.xchange.cobinhood.dto.CobinhoodResponse;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodCurrencies;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodOrderBook;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodTicker;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodTickers;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodTrades;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodTradingPairs;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

public class CobinhoodMarketDataServiceRaw extends CobinhoodBaseService {

  protected CobinhoodMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CobinhoodResponse<CobinhoodTicker.Container> getCobinhoodTicker(CurrencyPair currencyPair)
      throws IOException {
    try {
      return checkSuccess(cobinhood.tick(CobinhoodAdapters.adaptCurrencyPair(currencyPair)));
    } catch (CobinhoodException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  public CobinhoodResponse<CobinhoodTickers> getCobinhoodTickers() throws IOException {
    try {
      return checkSuccess(cobinhood.tick());
    } catch (CobinhoodException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  public CobinhoodResponse<CobinhoodOrderBook.Container> getCobinhoodOrderBook(
      CurrencyPair currencyPair) throws IOException {
    return getCobinhoodOrderBook(currencyPair, null);
  }

  public CobinhoodResponse<CobinhoodOrderBook.Container> getCobinhoodOrderBook(
      CurrencyPair currencyPair, Integer limit) throws IOException {
    try {
      return checkSuccess(
          cobinhood.orders(CobinhoodAdapters.adaptCurrencyPair(currencyPair), limit));
    } catch (CobinhoodException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  public CobinhoodResponse<CobinhoodTrades> getCobinhoodTrades(
      CurrencyPair currencyPair, Integer limit) throws IOException {
    try {
      return checkSuccess(
          cobinhood.trades(CobinhoodAdapters.adaptCurrencyPair(currencyPair), limit));
    } catch (CobinhoodException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  public CobinhoodResponse<CobinhoodCurrencies> getCobinhoodCurrencies() throws IOException {
    try {
      return checkSuccess(cobinhood.currencies());
    } catch (CobinhoodException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  public CobinhoodResponse<CobinhoodTradingPairs> getCobinhoodTradingPairs() throws IOException {
    try {
      return checkSuccess(cobinhood.tradingPairs());
    } catch (CobinhoodException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  public static <D> CobinhoodResponse<D> checkSuccess(CobinhoodResponse<D> response) {

    if (response.isSuccess()) {
      return response;
    } else {
      throw new ExchangeException(response.getError().getCode());
    }
  }
}
