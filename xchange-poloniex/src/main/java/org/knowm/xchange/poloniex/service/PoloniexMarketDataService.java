package org.knowm.xchange.poloniex.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.poloniex.PoloniexAdapters;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexDepth;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author Zach Holmes
 */

public class PoloniexMarketDataService extends PoloniexMarketDataServiceRaw implements MarketDataService {

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

    int depthLimit = 999999; // ~full order book
    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer) {

        depthLimit = (Integer) args[0];
      } else {
        throw new ExchangeException("Orderbook size argument must be an Integer!");
      }
    }
    depth = getPoloniexDepth(currencyPair, depthLimit);
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
