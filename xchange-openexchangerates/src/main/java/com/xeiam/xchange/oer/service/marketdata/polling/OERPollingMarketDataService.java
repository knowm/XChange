/**
 * Copyright 2012 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xeiam.xchange.oer.service.marketdata.polling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author timmolter
 */
public class OERPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService, CachedDataSession {

  private final Logger logger = LoggerFactory.getLogger(OERPollingMarketDataService.class);

  private final OER openExchangeRates;

  /**
   * time stamps used to pace API calls
   */
  private long tickerRequestTimeStamp = 0L;

  private OERTickers cachedOERTickers;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The exchange specification
   */
  public OERPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.openExchangeRates = RestProxyFactory.createProxy(OER.class, exchangeSpecification.getUri());
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
      tickerRequestTimeStamp = System.currentTimeMillis();

      // Request data
      cachedOERTickers = openExchangeRates.getTickers(exchangeSpecification.getApiKey());
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
    // System.out.println(exchangeRate);

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
  public Trades getTrades(String tradableIdentifier, String currency) {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * Verify
   * 
   * @param tradableIdentifier
   * @param currency
   */
  private void verify(String tradableIdentifier, String currency) {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.isTrue(currency.equals("USD"), "Base curreny must be USD for this exchange");
    Assert.isTrue(OERUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

}
