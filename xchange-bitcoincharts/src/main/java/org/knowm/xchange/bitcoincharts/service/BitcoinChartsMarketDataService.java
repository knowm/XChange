package org.knowm.xchange.bitcoincharts.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoincharts.BitcoinCharts;
import org.knowm.xchange.bitcoincharts.BitcoinChartsAdapters;
import org.knowm.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import si.mazi.rescu.RestProxyFactory;

/** @author timmolter */
public class BitcoinChartsMarketDataService extends BitcoinChartsBaseService
    implements MarketDataService {

  private final BitcoinCharts bitcoinCharts;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoinChartsMarketDataService(Exchange exchange) {

    super(exchange);
    this.bitcoinCharts =
        RestProxyFactory.createProxy(
            BitcoinCharts.class,
            exchange.getExchangeSpecification().getPlainTextUri(),
            getClientConfig());
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
