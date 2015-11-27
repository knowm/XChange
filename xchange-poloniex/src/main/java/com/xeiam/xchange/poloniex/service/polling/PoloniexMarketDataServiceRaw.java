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

  public PoloniexTicker getPoloniexTicker(CurrencyPair currencyPair) throws IOException {

    String command = "returnTicker";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    HashMap<String, PoloniexMarketData> marketData;
    try {
      marketData = poloniex.getTicker(command);
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }

    PoloniexMarketData data = marketData.get(pairString);

    if (data == null)
      throw new ExchangeException(currencyPair + " not available");

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

}
