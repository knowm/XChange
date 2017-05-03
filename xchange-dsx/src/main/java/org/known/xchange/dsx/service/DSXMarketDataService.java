package org.known.xchange.dsx.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.known.xchange.dsx.DSXAdapters;
import org.known.xchange.dsx.dto.marketdata.DSXOrderbookWrapper;
import org.known.xchange.dsx.dto.marketdata.DSXTickerWrapper;
import org.known.xchange.dsx.dto.marketdata.DSXTrade;

/**
 * @author Mikhail Wall
 */

public class DSXMarketDataService extends DSXMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public DSXMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = DSXAdapters.getPair(currencyPair);
    DSXTickerWrapper dsxTickerWrapper = getDSXTicker(pairs);

    return DSXAdapters.adaptTicker(dsxTickerWrapper.getTicker(DSXAdapters.getPair(currencyPair)), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = DSXAdapters.getPair(currencyPair);
    DSXOrderbookWrapper dsxOrderbookWrapper = getDSXOrderbook(pairs);

    List<LimitOrder> asks = DSXAdapters.adaptOrders(dsxOrderbookWrapper.getOrderbook(DSXAdapters.getPair(currencyPair)).getAsks(), currencyPair,
        "ask", "");
    List<LimitOrder> bids = DSXAdapters.adaptOrders(dsxOrderbookWrapper.getOrderbook(DSXAdapters.getPair(currencyPair)).getBids(), currencyPair,
        "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = DSXAdapters.getPair(currencyPair);
    int numberOfItems = -1;
    try {
      numberOfItems = (Integer) args[0];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no argument given.
    }
    DSXTrade[] dsxTrades = null;

    if (numberOfItems == -1) {
      dsxTrades = getDSXTrades(pairs, FULL_SIZE).getTrades(DSXAdapters.getPair(currencyPair));
    } else {
      dsxTrades = getDSXTrades(pairs, numberOfItems).getTrades(DSXAdapters.getPair(currencyPair));
    }
    return DSXAdapters.adaptTrades(dsxTrades, currencyPair);
  }
}
