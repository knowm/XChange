package com.xeiam.xchange.poloniex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexDepth;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexTicker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Zach Holmes
 */

public class PoloniexMarketDataService extends PoloniexMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    PoloniexTicker poloniexTicker = getPoloniexTicker(currencyPair);
    Ticker ticker = PoloniexAdapters.adaptPoloniexTicker(poloniexTicker, currencyPair);
    return ticker;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {

    PoloniexDepth depth = null;

    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer) {
        int depthLimit = (Integer) args[0];
        depth = getPoloniexDepth(currencyPair, depthLimit);
      } else {
        throw new ExchangeException("Orderbook size argument must be an Integer!");
      }
    }
    if (depth == null) {
      depth = getPoloniexDepth(currencyPair);
    }
    OrderBook orderBook = PoloniexAdapters.adaptPoloniexDepth(depth, currencyPair);
    return orderBook;
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {

    Long startTime = null;
    Long endTime = null;

    if (args != null) {
      switch (args.length) {
      case 2:
        if (args[1] != null && args[1] instanceof Long) {
          endTime = (Long) args[1];
        }
      case 1:
        if (args[0] != null && args[0] instanceof Long) {
          startTime = (Long) args[0];
        }
      }
    }
    PoloniexPublicTrade[] poloniexPublicTrades = null;
    if (startTime == null && endTime == null) {
      poloniexPublicTrades = getPoloniexPublicTrades(currencyPair);
    } else {
      poloniexPublicTrades = getPoloniexPublicTrades(currencyPair, startTime, endTime);
    }
    Trades trades = PoloniexAdapters.adaptPoloniexPublicTrades(poloniexPublicTrades, currencyPair);
    return trades;
  }

}
