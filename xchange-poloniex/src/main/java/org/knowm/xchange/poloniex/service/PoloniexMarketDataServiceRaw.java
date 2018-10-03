package org.knowm.xchange.poloniex.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexErrorAdapter;
import org.knowm.xchange.poloniex.PoloniexUtils;
import org.knowm.xchange.poloniex.dto.PoloniexException;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexCurrencyInfo;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexDepth;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexTicker;

public class PoloniexMarketDataServiceRaw extends PoloniexBaseService {

  private final long cache_delay = 1000L;
  private HashMap<String, PoloniexMarketData> TickermarketData;
  private long next_refresh = System.currentTimeMillis() + cache_delay;

  // There is no point to query the ticker instantly again when
  // all tickers are returned on 1 query available in the hash map
  // let's wait a seconds and save our self a call for each ticker in our calling for loop.

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
    return poloniex.getCurrencyInfo(command);
  }

  public Map<String, PoloniexMarketData> getAllPoloniexTickers() throws IOException {
    String command = "returnTicker";
    return poloniex.getTicker(command);
  }

  public PoloniexTicker getPoloniexTicker(CurrencyPair currencyPair) throws IOException {

    String command = "returnTicker";
    String pairString = PoloniexUtils.toPairString(currencyPair);
    long now = System.currentTimeMillis();

    if (TickermarketData == null || next_refresh < now) {
      try {
        TickermarketData = poloniex.getTicker(command);
      } catch (PoloniexException e) {
        throw PoloniexErrorAdapter.adapt(e);
      } finally {
        // also nice to take a short break on an error
        next_refresh = now + cache_delay;
      }
    }

    PoloniexMarketData data = TickermarketData.get(pairString);
    if (data == null) {
      return null;
    }
    return new PoloniexTicker(data, currencyPair);
  }

  public PoloniexDepth getPoloniexDepth(CurrencyPair currencyPair) throws IOException {

    String command = "returnOrderBook";
    String pairString = PoloniexUtils.toPairString(currencyPair);
    return poloniex.getOrderBook(command, pairString);
  }

  public PoloniexDepth getPoloniexDepth(CurrencyPair currencyPair, int depth) throws IOException {

    String command = "returnOrderBook";
    String pairString = PoloniexUtils.toPairString(currencyPair);
    return poloniex.getOrderBook(command, pairString, depth);
  }

  public Map<String, PoloniexDepth> getAllPoloniexDepths() throws IOException {

    String command = "returnOrderBook";
    return poloniex.getAllOrderBooks(command, "all", null);
  }

  public Map<String, PoloniexDepth> getAllPoloniexDepths(int depth) throws IOException {

    String command = "returnOrderBook";
    return poloniex.getAllOrderBooks(command, "all", depth);
  }

  public PoloniexPublicTrade[] getPoloniexPublicTrades(CurrencyPair currencyPair)
      throws IOException {

    String command = "returnTradeHistory";
    String pairString = PoloniexUtils.toPairString(currencyPair);
    return poloniex.getTrades(command, pairString, null, null);
  }

  public PoloniexPublicTrade[] getPoloniexPublicTrades(
      CurrencyPair currencyPair, Long startTime, Long endTime) throws IOException {

    String command = "returnTradeHistory";
    String pairString = PoloniexUtils.toPairString(currencyPair);
    return poloniex.getTrades(command, pairString, startTime, endTime);
  }

  public PoloniexChartData[] getPoloniexChartData(
      CurrencyPair currencyPair, Long startTime, Long endTime, PoloniexChartDataPeriodType period)
      throws IOException {

    String command = "returnChartData";
    String pairString = PoloniexUtils.toPairString(currencyPair);
    return poloniex.getChartData(command, pairString, startTime, endTime, period.getPeriod());
  }
}
