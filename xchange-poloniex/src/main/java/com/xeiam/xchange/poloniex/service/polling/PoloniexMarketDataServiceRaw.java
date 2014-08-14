package com.xeiam.xchange.poloniex.service.polling;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.poloniex.Poloniex;
import com.xeiam.xchange.poloniex.PoloniexUtils;
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

  public Map<String, PoloniexDepth> getAllPoloniexDepths() throws IOException {

    String command = "returnOrderBook";
    
    Map<String, PoloniexDepth> depths = poloniex.getAllOrderBooks(command, "all");

    return depths;
  }

  public PoloniexPublicTrade[] getPoloniexPublicTrades(CurrencyPair currencyPair) throws IOException {

    String command = "returnTradeHistory";
    String pairString = PoloniexUtils.toPairString(currencyPair);

    PoloniexPublicTrade[] trades = poloniex.getTrades(command, pairString);
    return trades;
  }

}
