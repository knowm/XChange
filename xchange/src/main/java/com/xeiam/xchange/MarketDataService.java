package com.xeiam.xchange;

import com.xeiam.xchange.dto.marketdata.Ticker;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Set;

/**
 * <p>Interface to provide the following to {@link Exchange}:</p>
 * <ul>
 * <li>Standard methods available to explore the market data</li>
 * </ul>
 *
 * @since 0.0.1
 *
 * TODO Consider the blocking nature and perhaps go for a
 * TODO MarketDataCallback or MarketDataListener approach?
 *         
 */
public interface MarketDataService {

  /**
   * @return A collection of {@link Ticker}s representing the latest market data 
   */
  Collection<Ticker> getLatestMarketData();

  /**
   * @return A collection of {@link Ticker}s representing the market data within the range
   */
  Collection<Ticker> getHistoricalMarketData(DateTime validFrom, DateTime validTo);

  Set<String> getExchangeSymbols();

  Ticker getMarketDepth(String symbol);

  Ticker getTrades(String symbol);

  Ticker getMarketFullDepth(String symbol);

  Ticker getCancelledTrades(String symbol);

  Ticker getTick(String symbol);
}
