package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXAdapters;
import org.knowm.xchange.dsx.dto.marketdata.DSXOrderbook;
import org.knowm.xchange.dsx.dto.marketdata.DSXOrderbookWrapper;
import org.knowm.xchange.dsx.dto.marketdata.DSXTickerWrapper;
import org.knowm.xchange.dsx.dto.marketdata.DSXTrade;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author Mikhail Wall */
public class DSXMarketDataService extends DSXMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public DSXMarketDataService(Exchange exchange) {

    super(exchange);
  }

  /**
   * Get ticker from exchange
   *
   * @param currencyPair
   * @param args
   * @return The ticker
   * @throws IOException
   */
  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    String marketName = DSXAdapters.currencyPairToMarketName(currencyPair);
    String accountType = null;
    try {
      if (args != null) {
        accountType = (String) args[0];
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no argument given.
    }

    DSXTickerWrapper dsxTickerWrapper = getDSXTicker(marketName, accountType);

    // Adapt to XChange DTOs
    return DSXAdapters.adaptTicker(dsxTickerWrapper.getTicker(marketName), currencyPair);
  }

  /**
   * Get market depth from exchange
   *
   * @param currencyPair Currency pair for getting info about
   * @param args Optional arguments. Exchange-specific
   * @return The OrderBook
   * @throws IOException
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    String marketName = DSXAdapters.currencyPairToMarketName(currencyPair);

    String accountType = null;
    try {
      if (args != null) {
        accountType = (String) args[0];
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no argument given.
    }

    DSXOrderbookWrapper dsxOrderbookWrapper = getDSXOrderbook(marketName, accountType);
    DSXOrderbook orderbook = dsxOrderbookWrapper.getOrderbook(marketName);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = DSXAdapters.adaptOrders(orderbook.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = DSXAdapters.adaptOrders(orderbook.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  /**
   * Get recent trades from exchange
   *
   * @param currencyPair
   * @param args Optional arguments. Exchange-specific. This implementation assumes args[0] is
   *     integer value
   * @return Trades object
   * @throws IOException
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    String marketName = DSXAdapters.currencyPairToMarketName(currencyPair);

    int numberOfItems = -1;
    try {
      if (args != null) {
        numberOfItems =
            (Integer)
                args[0]; // can this really be args[0] if we are also using args[0] as a string//
        // below??
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no argument given.
    }
    DSXTrade[] dsxTrades;

    String accountType = null;
    try {
      if (args != null) {
        accountType = (String) args[1];
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no argument given.
    }

    if (numberOfItems == -1) {
      dsxTrades = getDSXTrades(marketName, FULL_SIZE, accountType).getTrades(marketName);
    } else {
      dsxTrades = getDSXTrades(marketName, numberOfItems, accountType).getTrades(marketName);
    }
    return DSXAdapters.adaptTrades(dsxTrades, currencyPair);
  }
}
