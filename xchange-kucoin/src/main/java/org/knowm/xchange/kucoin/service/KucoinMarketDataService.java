package org.knowm.xchange.kucoin.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.kucoin.dto.KucoinAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * <p>
 * Implementation of the market data service for Bittrex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class KucoinMarketDataService extends KucoinMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KucoinMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return KucoinAdapters.adaptTicker(
        tick(currencyPair),
        currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer limit = null;
    
    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer && (Integer) args[0] > 0) {
        limit = (Integer) args[0];
      }
    }
    return KucoinAdapters.adaptOrderBook(
        orders(currencyPair, limit),
        currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }
}
