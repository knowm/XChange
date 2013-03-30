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
package com.xeiam.xchange.bitcoincharts.service.marketdata.polling;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitcoincharts.BitcoinCharts;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsAdapters;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsUtils;
import com.xeiam.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author timmolter
 */
public class BitcoinChartsPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService, CachedDataSession {

  private final Logger logger = LoggerFactory.getLogger(BitcoinChartsPollingMarketDataService.class);

  private final BitcoinCharts bitcoinCharts;

  /**
   * time stamps used to pace API calls
   */
  private long tickerRequestTimeStamp = 0L;

  private BitcoinChartsTicker[] cachedBitcoinChartsTickers;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The exchange specification
   */
  public BitcoinChartsPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitcoinCharts = RestProxyFactory.createProxy(BitcoinCharts.class, exchangeSpecification.getUri());
  }

  @Override
  public long getRefreshRate() {

    return BitcoinChartsUtils.REFRESH_RATE_MILLIS;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return BitcoinChartsUtils.CURRENCY_PAIRS;
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    // check for pacing violation
    if (tickerRequestTimeStamp == 0L || System.currentTimeMillis() - tickerRequestTimeStamp >= getRefreshRate()) {

      logger.debug("requesting BitcoinCharts tickers");
      tickerRequestTimeStamp = System.currentTimeMillis();

      // Request data
      cachedBitcoinChartsTickers = bitcoinCharts.getMarketData();
    }

    return BitcoinChartsAdapters.adaptTicker(cachedBitcoinChartsTickers, tradableIdentifier);
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
    Assert.isTrue(currency.equals(Currencies.BTC), "Base curreny must be " + Currencies.BTC + " for this exchange");
    Assert.isTrue(BitcoinChartsUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

}
