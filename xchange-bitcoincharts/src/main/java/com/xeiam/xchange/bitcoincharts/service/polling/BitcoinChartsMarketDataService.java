package com.xeiam.xchange.bitcoincharts.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcoincharts.BitcoinCharts;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsAdapters;
import com.xeiam.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author timmolter
 */
public class BitcoinChartsMarketDataService extends BitcoinChartsBasePollingService implements PollingMarketDataService {

  private final BitcoinCharts bitcoinCharts;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoinChartsMarketDataService(Exchange exchange) {

    super(exchange);
    this.bitcoinCharts = RestProxyFactory.createProxy(BitcoinCharts.class, exchange.getExchangeSpecification().getPlainTextUri());
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    BitcoinChartsTicker[] bitcoinChartsTickers = getBitcoinChartsTickers();

    return BitcoinChartsAdapters.adaptTicker(bitcoinChartsTickers, currencyPair);
  }

  public BitcoinChartsTicker[] getBitcoinChartsTickers() throws IOException {
    return bitcoinCharts.getMarketData();
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
