/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.oer.service.polling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.oer.OER;
import com.xeiam.xchange.oer.OERAdapters;
import com.xeiam.xchange.oer.OERUtils;
import com.xeiam.xchange.oer.dto.marketdata.OERTickers;
import com.xeiam.xchange.oer.dto.marketdata.Rates;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author timmolter
 */
public class OERPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService, CachedDataSession {

  private final Logger logger = LoggerFactory.getLogger(OERPollingMarketDataService.class);

  private final OER openExchangeRates;

  /**
   * Time stamps used to pace API calls
   */
  private long tickerRequestTimeStamp = 0L;

  private OERTickers cachedOERTickers;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public OERPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.openExchangeRates = RestProxyFactory.createProxy(OER.class, exchangeSpecification.getPlainTextUri());
  }

  @Override
  public long getRefreshRate() {

    return OERUtils.REFRESH_RATE_MILLIS;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return OERUtils.CURRENCY_PAIRS;
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    // check for pacing violation
    if (tickerRequestTimeStamp == 0L || System.currentTimeMillis() - tickerRequestTimeStamp >= getRefreshRate()) {

      logger.debug("requesting OER tickers");

      // Request data
      cachedOERTickers = openExchangeRates.getTickers(exchangeSpecification.getApiKey());
      if (cachedOERTickers == null) {
        throw new ExchangeException("Null response returned from Open Exchange Rates!");
      }

      // if we make it here, set the timestamp so we know a cached request has been successful
      tickerRequestTimeStamp = System.currentTimeMillis();
    }

    Rates rates = cachedOERTickers.getRates();

    // Use reflection to get at data.
    Method method = null;
    try {
      method = Rates.class.getMethod("get" + tradableIdentifier, null);
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
    return OERAdapters.adaptTicker(tradableIdentifier, exchangeRate, cachedOERTickers.getTimestamp() * 1000L);
  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency, Object... args) {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * Verify
   * 
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @param currency The transaction currency (e.g. USD in BTC/USD)
   */
  private void verify(String tradableIdentifier, String currency) {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.isTrue(currency.equals("USD"), "Base curreny must be USD for this exchange");
    Assert.isTrue(OERUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

}
