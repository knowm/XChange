package org.knowm.xchange.btce.v3.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.BTCEAdapters;
import org.knowm.xchange.btce.v3.dto.marketdata.BTCEDepthWrapper;
import org.knowm.xchange.btce.v3.dto.marketdata.BTCETickerWrapper;
import org.knowm.xchange.btce.v3.dto.marketdata.BTCETrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BTCEMarketDataService extends BTCEMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCEMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = BTCEAdapters.getPair(currencyPair);
    BTCETickerWrapper btceTickerWrapper = getBTCETicker(pairs);

    // Adapt to XChange DTOs
    return BTCEAdapters.adaptTicker(btceTickerWrapper.getTicker(BTCEAdapters.getPair(currencyPair)), currencyPair);
  }

  /**
   * Get market depth from exchange
   *
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG). First currency of the pair
   * @param currency The currency of interest, null if irrelevant. Second currency of the pair
   * @param args Optional arguments. Exchange-specific. This implementation assumes: Integer value from 1 to 2000 -> get corresponding number of items
   * @return The OrderBook
   * @throws IOException
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = BTCEAdapters.getPair(currencyPair);
    BTCEDepthWrapper btceDepthWrapper = null;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer) || ((Integer) arg0 < 1) || ((Integer) arg0 > FULL_SIZE)) {
        throw new ExchangeException("Orderbook size argument must be an Integer in the range: (1, 2000)!");
      } else {
        btceDepthWrapper = getBTCEDepth(pairs, (Integer) arg0);
      }
    } else { // default to full orderbook
      btceDepthWrapper = getBTCEDepth(pairs, FULL_SIZE);
    }

    // Adapt to XChange DTOs
    List<LimitOrder> asks = BTCEAdapters.adaptOrders(btceDepthWrapper.getDepth(BTCEAdapters.getPair(currencyPair)).getAsks(), currencyPair, "ask",
        "");
    List<LimitOrder> bids = BTCEAdapters.adaptOrders(btceDepthWrapper.getDepth(BTCEAdapters.getPair(currencyPair)).getBids(), currencyPair, "bid",
        "");

    return new OrderBook(null, asks, bids);
  }

  /**
   * Get recent trades from exchange
   *
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest
   * @param args Optional arguments. This implementation assumes args[0] is integer value limiting number of trade items to get. -1 or missing -> use
   *        default 2000 max fetch value int from 1 to 2000 -> use API v.3 to get corresponding number of trades
   * @return Trades object
   * @throws IOException
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = BTCEAdapters.getPair(currencyPair);
    int numberOfItems = -1;
    try {
      numberOfItems = (Integer) args[0];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no arg given.
    }
    BTCETrade[] bTCETrades = null;

    if (numberOfItems == -1) {
      bTCETrades = getBTCETrades(pairs, FULL_SIZE).getTrades(BTCEAdapters.getPair(currencyPair));
    } else {
      bTCETrades = getBTCETrades(pairs, numberOfItems).getTrades(BTCEAdapters.getPair(currencyPair));
    }

    return BTCEAdapters.adaptTrades(bTCETrades, currencyPair);
  }

}
