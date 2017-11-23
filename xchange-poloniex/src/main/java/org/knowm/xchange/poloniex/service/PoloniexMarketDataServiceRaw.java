package org.knowm.xchange.poloniex.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.poloniex.PoloniexException;
import org.knowm.xchange.poloniex.PoloniexUtils;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexCurrencyInfo;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexDepth;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexTicker;

public class PoloniexMarketDataServiceRaw extends PoloniexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<String, PoloniexCurrencyInfo> getPoloniexCurrencyInfo() throws IOException {

    String command = "returnCurrencies";

    try {
      Map<String, PoloniexCurrencyInfo> currencyInfo = poloniex.getCurrencyInfo(command);
      return currencyInfo;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }

  public Map<String, PoloniexMarketData> getAllPoloniexTickers() throws IOException {

    String command = "returnTicker";

    try {
      Map<String, PoloniexMarketData> marketData = poloniex.getTicker(command);
      return marketData;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    }

  }

  // There is no point to query the ticker instantly again when
  // all tickers are returned on 1 query available in the hash map
  // let's wait a seconds and save our self a call for each ticker in our calling for loop.

  private HashMap<String, PoloniexMarketData> TickermarketData;
  private final long cache_delay = 1000L;
  private long next_refresh = System.currentTimeMillis() + cache_delay;

  public PoloniexTicker getPoloniexTicker(CurrencyPair currencyPair) throws IOException {

    String command = "returnTicker";
    String pairString = PoloniexUtils.toPairString(currencyPair);
    long now = System.currentTimeMillis();

    if (TickermarketData == null || next_refresh < now) {
      try {
        TickermarketData = poloniex.getTicker(command);
      } catch (PoloniexException e) {
        throw new ExchangeException(e.getError(), e);
      } finally {
        // also nice to take a short break on an error
        next_refresh = now + cache_delay;
      }
    }

    PoloniexMarketData data = TickermarketData.get(pairString);
    if (data == null) {
      throw new ExchangeException(currencyPair + " not available");
    }

    return new PoloniexTicker(data, currencyPair);
  }

  public PoloniexDepth getPoloniexDepth(CurrencyPair currencyPair) throws IOException {

    String command = "returnOrderBook";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    try {
      PoloniexDepth depth = poloniex.getOrderBook(command, pairString);
      return depth;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }

  public PoloniexDepth getPoloniexDepth(CurrencyPair currencyPair, int depth) throws IOException {

    String command = "returnOrderBook";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    try {
      PoloniexDepth limitDepth = poloniex.getOrderBook(command, pairString, depth);
      return limitDepth;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }

  public Map<String, PoloniexDepth> getAllPoloniexDepths() throws IOException {

    String command = "returnOrderBook";

    try {
      Map<String, PoloniexDepth> depths = poloniex.getAllOrderBooks(command, "all", null);
      return depths;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }

  public Map<String, PoloniexDepth> getAllPoloniexDepths(int depth) throws IOException {

    String command = "returnOrderBook";

    try {
      Map<String, PoloniexDepth> depths = poloniex.getAllOrderBooks(command, "all", depth);
      return depths;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }

  public PoloniexPublicTrade[] getPoloniexPublicTrades(CurrencyPair currencyPair) throws IOException {

    String command = "returnTradeHistory";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    try {
      PoloniexPublicTrade[] trades = poloniex.getTrades(command, pairString, null, null);
      return trades;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }

  public PoloniexPublicTrade[] getPoloniexPublicTrades(CurrencyPair currencyPair, Long startTime, Long endTime) throws IOException {

    String command = "returnTradeHistory";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    try {
      PoloniexPublicTrade[] trades = poloniex.getTrades(command, pairString, startTime, endTime);
      return trades;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }

  public PoloniexChartData[] getPoloniexChartData(CurrencyPair currencyPair, Long startTime, Long endTime,
      PoloniexChartDataPeriodType period) throws IOException {

    String command = "returnChartData";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    try {
      PoloniexChartData[] chartData = poloniex.getChartData(command, pairString, startTime, endTime, period.getPeriod());
      return chartData;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }
}
