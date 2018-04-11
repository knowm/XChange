package org.knowm.xchange.bitcoinaverage.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinaverage.BitcoinAverageAdapters;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Implementation of the generic market data service for BitcoinAverage
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class BitcoinAverageMarketDataService extends BitcoinAverageMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoinAverageMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    BitcoinAverageTicker bitcoinAverageTicker =
        getBitcoinAverageTicker(
            currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    // Adapt to XChange DTOs
    return BitcoinAverageAdapters.adaptTicker(bitcoinAverageTicker, currencyPair);
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
