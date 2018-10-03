package org.knowm.xchange.oer.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.oer.OERAdapters;
import org.knowm.xchange.oer.dto.marketdata.OERRates;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class OERMarketDataService extends OERMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public OERMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    OERRates rates = getOERTicker();

    // Use reflection to get at data.
    Method method = null;
    try {
      method = OERRates.class.getMethod("get" + currencyPair.base.getCurrencyCode(), null);
    } catch (SecurityException | NoSuchMethodException e) {
      throw new ExchangeException("Problem getting exchange rate!", e);
    }

    Double exchangeRate = null;
    try {
      exchangeRate = (Double) method.invoke(rates, null);
    } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
      throw new ExchangeException("Problem getting exchange rate!", e);
    }

    // Adapt to XChange DTOs
    return OERAdapters.adaptTicker(currencyPair, exchangeRate);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
