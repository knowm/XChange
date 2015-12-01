package com.xeiam.xchange.oer.service.polling;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.oer.OERAdapters;
import com.xeiam.xchange.oer.dto.marketdata.OERRates;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author timmolter
 */
public class OERMarketDataService extends OERMarketDataServiceRaw implements PollingMarketDataService {

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
    } catch (SecurityException e) {
      throw new ExchangeException("Problem getting exchange rate!", e);
    } catch (NoSuchMethodException e) {
      throw new ExchangeException("Problem getting exchange rate!", e);
    }

    Double exchangeRate = null;
    try {
      exchangeRate = (Double) method.invoke(rates, null);
    } catch (IllegalArgumentException e) {
      throw new ExchangeException("Problem getting exchange rate!", e);
    } catch (IllegalAccessException e) {
      throw new ExchangeException("Problem getting exchange rate!", e);
    } catch (InvocationTargetException e) {
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
