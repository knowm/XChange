package com.xeiam.xchange;

import com.xeiam.xchange.dto.marketdata.Tick;
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
   * @return A collection of {@link Tick}s representing the latest market data 
   */
  Collection<Tick> getLatestMarketData();

  /**
   * @return A collection of {@link Tick}s representing the market data within the range
   */
  Collection<Tick> getHistoricalMarketData(DateTime validFrom, DateTime validTo);

  Set<String> getExchangeSymbols();

  Tick getMarketDepth(String symbol);

  Tick getTrades(String symbol);

  Tick getMarketFullDepth(String symbol);

  Tick getCancelledTrades(String symbol);

  Tick getTick(String symbol);
}
