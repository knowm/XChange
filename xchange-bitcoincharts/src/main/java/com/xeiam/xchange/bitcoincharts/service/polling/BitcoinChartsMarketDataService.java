package com.xeiam.xchange.bitcoincharts.service.polling;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcoincharts.BitcoinCharts;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsAdapters;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsUtils;
import com.xeiam.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author timmolter
 */
public class BitcoinChartsMarketDataService extends BitcoinChartsBasePollingService implements PollingMarketDataService, CachedDataSession {

  private final Logger logger = LoggerFactory.getLogger(BitcoinChartsMarketDataService.class);

  private final BitcoinCharts bitcoinCharts;

  /**
   * time stamps used to pace API calls
   */
  private long tickerRequestTimeStamp = 0L;

  private BitcoinChartsTicker[] cachedBitcoinChartsTickers;

  /**
   *
   * Constructor
   *
   * @param exchange
   */
  public BitcoinChartsMarketDataService(Exchange exchange) {

    super(exchange);
    this.bitcoinCharts = RestProxyFactory.createProxy(BitcoinCharts.class, exchange.getExchangeSpecification().getPlainTextUri());
  }

  @Override
  public long getRefreshRate() {

    return BitcoinChartsUtils.REFRESH_RATE_MILLIS;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // check for pacing violation
    if (tickerRequestTimeStamp == 0L || System.currentTimeMillis() - tickerRequestTimeStamp >= getRefreshRate()) {

      logger.debug("requesting BitcoinCharts tickers");
      tickerRequestTimeStamp = System.currentTimeMillis();

      // Request data
      cachedBitcoinChartsTickers = bitcoinCharts.getMarketData();
    }

    return BitcoinChartsAdapters.adaptTicker(cachedBitcoinChartsTickers, currencyPair);
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
