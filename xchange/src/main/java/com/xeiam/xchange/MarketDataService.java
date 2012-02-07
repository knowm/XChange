package com.xeiam.xchange;

import java.util.Collection;
import java.util.Set;

import org.joda.time.DateTime;

import com.xeiam.xchange.dto.marketdata.CancelledTrades;
import com.xeiam.xchange.dto.marketdata.Depth;
import com.xeiam.xchange.dto.marketdata.FullDepth;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;

/**
 * <p>
 * Interface to provide the following to {@link Exchange}:
 * </p>
 * <ul>
 * <li>Standard methods available to explore the market data</li>
 * </ul>
 * 
 * @since 0.0.1 TODO Consider the blocking nature and perhaps go for a TODO MarketDataCallback or MarketDataListener approach?  
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

  Depth getDepth(String symbol);

  Trades getTrades(String symbol);

  FullDepth getFullDepth(String symbol);

  CancelledTrades getCancelledTrades(String symbol);

  Ticker getTicker(String symbol);
}
