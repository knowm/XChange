package com.xeiam.xchange.poloniex.service.polling;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.poloniex.PoloniexException;
import com.xeiam.xchange.poloniex.PoloniexUtils;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexCurrencyInfo;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexDepth;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexTicker;

public class PoloniexMarketDataServiceRaw extends PoloniexBasePollingService {

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
      throw new ExchangeException(e.getError());
    }
  }

  public Map<String, PoloniexMarketData> getAllPoloniexTickers() throws IOException {

    String command = "returnTicker";

    try {
      Map<String, PoloniexMarketData> marketData = poloniex.getTicker(command);
      return marketData;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }

  }

    // There is no point to query the ticker instantly again when
    // all tickers are returned on 1 query avaialble in the hash map
    // lets wait a seconds and save our self a call for each ticker in our calling for loop.
    
    private HashMap<String, PoloniexMarketData> TickermarketData;
    private final long cashe_delay = 1000L;
    private long next_refresh = System.currentTimeMillis() + cashe_delay;

    public PoloniexTicker getPoloniexTicker(CurrencyPair currencyPair) throws IOException {

        String command = "returnTicker";
        String pairString = PoloniexUtils.toPairString(currencyPair);
        long now = System.currentTimeMillis();

        if (TickermarketData == null || next_refresh < now) {
            try {
                TickermarketData = poloniex.getTicker(command);
            } catch (PoloniexException e) {
                throw new ExchangeException(e.getError());
            } finally {
                // also nice to take a short break on an error
                next_refresh = now + cashe_delay;
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
      throw new ExchangeException(e.getError());
    }
  }

  public PoloniexDepth getPoloniexDepth(CurrencyPair currencyPair, int depth) throws IOException {

    String command = "returnOrderBook";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    try {
      PoloniexDepth limitDepth = poloniex.getOrderBook(command, pairString, depth);
      return limitDepth;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public Map<String, PoloniexDepth> getAllPoloniexDepths() throws IOException {

    String command = "returnOrderBook";

    try {
      Map<String, PoloniexDepth> depths = poloniex.getAllOrderBooks(command, "all", null);
      return depths;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public Map<String, PoloniexDepth> getAllPoloniexDepths(int depth) throws IOException {

    String command = "returnOrderBook";

    try {
      Map<String, PoloniexDepth> depths = poloniex.getAllOrderBooks(command, "all", depth);
      return depths;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public PoloniexPublicTrade[] getPoloniexPublicTrades(CurrencyPair currencyPair) throws IOException {

    String command = "returnTradeHistory";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    try {
      PoloniexPublicTrade[] trades = poloniex.getTrades(command, pairString, null, null);
      return trades;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public PoloniexPublicTrade[] getPoloniexPublicTrades(CurrencyPair currencyPair, Long startTime, Long endTime) throws IOException {

    String command = "returnTradeHistory";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    try {
      PoloniexPublicTrade[] trades = poloniex.getTrades(command, pairString, startTime, endTime);
      return trades;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public PoloniexChartData[] getPoloniexChartData(CurrencyPair currencyPair, Integer period, Long startTime, Long endTime) throws IOException {
    String command = "returnChartData";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    try {
      PoloniexChartData[] chartData = poloniex.getChartData(command, period, pairString, startTime, endTime);
      return chartData;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }
}
