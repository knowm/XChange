package com.xeiam.xchange.poloniex.service.polling;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Candlestick;
import com.xeiam.xchange.dto.marketdata.CandlestickPeriod;
import com.xeiam.xchange.poloniex.Poloniex;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.PoloniexUtils;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexCandlestick;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexCurrencyInfo;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexDepth;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexTicker;

/**
 * <p>
 * Implementation of the market data service for Poloniex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class PoloniexMarketDataServiceRaw extends PoloniexBasePollingService<Poloniex> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public PoloniexMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(Poloniex.class, exchangeSpecification);
  }

  public Map<String, PoloniexCurrencyInfo> getPoloniexCurrencyInfo() throws IOException {

    String command = "returnCurrencies";

    Map<String, PoloniexCurrencyInfo> currencyInfo = poloniex.getCurrencyInfo(command);

    return currencyInfo;

  }

  public Map<String, PoloniexMarketData> getAllPoloniexTickers() throws IOException {

    String command = "returnTicker";

    Map<String, PoloniexMarketData> marketData = poloniex.getTicker(command);

    return marketData;

  }

  public PoloniexTicker getPoloniexTicker(CurrencyPair currencyPair) throws IOException {

    String command = "returnTicker";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    HashMap<String, PoloniexMarketData> marketData = poloniex.getTicker(command);

    PoloniexMarketData data = marketData.get(pairString);

    return new PoloniexTicker(data, currencyPair);

  }

  public PoloniexDepth getPoloniexDepth(CurrencyPair currencyPair) throws IOException {

    String command = "returnOrderBook";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    PoloniexDepth depth = poloniex.getOrderBook(command, pairString);
    return depth;
  }

  public PoloniexDepth getPoloniexDepth(CurrencyPair currencyPair, int depth) throws IOException {

    String command = "returnOrderBook";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    PoloniexDepth limitDepth = poloniex.getOrderBook(command, pairString, depth);
    return limitDepth;
  }

  public Map<String, PoloniexDepth> getAllPoloniexDepths() throws IOException {

    String command = "returnOrderBook";

    Map<String, PoloniexDepth> depths = poloniex.getAllOrderBooks(command, "all", null);

    return depths;
  }

  public Map<String, PoloniexDepth> getAllPoloniexDepths(int depth) throws IOException {

    String command = "returnOrderBook";

    Map<String, PoloniexDepth> depths = poloniex.getAllOrderBooks(command, "all", depth);

    return depths;
  }

  public PoloniexPublicTrade[] getPoloniexPublicTrades(CurrencyPair currencyPair) throws IOException {

    String command = "returnTradeHistory";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    PoloniexPublicTrade[] trades = poloniex.getTrades(command, pairString, null, null);
    return trades;
  }

  public PoloniexPublicTrade[] getPoloniexPublicTrades(CurrencyPair currencyPair, Long startTime, Long endTime) throws IOException {

    String command = "returnTradeHistory";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    PoloniexPublicTrade[] trades = poloniex.getTrades(command, pairString, startTime, endTime);
    return trades;
  }

  public List<Candlestick> getPoloniexChart(CurrencyPair currencyPair, int bars, CandlestickPeriod period) throws IOException {

    String command = "returnChartData";
    String pairString = PoloniexUtils.toPairString(currencyPair);
    long currentTimeSeconds = System.currentTimeMillis() / 1000L;
    long startTime = currentTimeSeconds - ((bars + 2) * period.getSeconds());

    List<PoloniexCandlestick> poloniexCandlesticks = poloniex.getChartData(command, pairString, startTime, 9999999999L, period.getSeconds());

    while (poloniexCandlesticks.size() > bars) {

      poloniexCandlesticks.remove(0);
    }

    return PoloniexAdapters.adaptPoloniexCandlesticks(poloniexCandlesticks);
  }

}
