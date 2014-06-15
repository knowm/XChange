/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.BTCEUtils;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepthWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for BTCE
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BTCEMarketDataService extends BTCEMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = com.xeiam.xchange.btce.v3.BTCEUtils.getPair(currencyPair);
    BTCETickerWrapper btceTickerWrapper = getBTCETicker(pairs);

    // Adapt to XChange DTOs
    return BTCEAdapters.adaptTicker(btceTickerWrapper.getTicker(BTCEUtils.getPair(currencyPair)), currencyPair);
  }

  /**
   * Get market depth from exchange
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG). First currency of the pair
   * @param currency The currency of interest, null if irrelevant. Second currency of the pair
   * @param args Optional arguments. Exchange-specific. This implementation assumes:
   *          Integer value from 1 to 2000 -> get corresponding number of items
   * @return The OrderBook
   * @throws IOException
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = com.xeiam.xchange.btce.v3.BTCEUtils.getPair(currencyPair);
    BTCEDepthWrapper btceDepthWrapper = null;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer) || ((Integer) arg0 < 1) || ((Integer) arg0 > FULL_SIZE)) {
        throw new ExchangeException("Orderbook size argument must be an Integer in the range: (1, 2000)!");
      }
      else {
        btceDepthWrapper = getBTCEDepth(pairs, (Integer) arg0);
      }
    }
    else { // default to full orderbook
      btceDepthWrapper = getBTCEDepth(pairs, FULL_SIZE);
    }

    // Adapt to XChange DTOs
    List<LimitOrder> asks = BTCEAdapters.adaptOrders(btceDepthWrapper.getDepth(BTCEUtils.getPair(currencyPair)).getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = BTCEAdapters.adaptOrders(btceDepthWrapper.getDepth(BTCEUtils.getPair(currencyPair)).getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  /**
   * Get recent trades from exchange
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest
   * @param args Optional arguments. This implementation assumes
   *          args[0] is integer value limiting number of trade items to get.
   *          -1 or missing -> use default 2000 max fetch value
   *          int from 1 to 2000 -> use API v.3 to get corresponding number of trades
   * @return Trades object
   * @throws IOException
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = com.xeiam.xchange.btce.v3.BTCEUtils.getPair(currencyPair);
    int numberOfItems = -1;
    try {
      numberOfItems = (Integer) args[0];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no arg given.
    }
    BTCETrade[] bTCETrades = null;

    if (numberOfItems == -1) {
      bTCETrades = getBTCETrades(pairs, FULL_SIZE).getTrades(com.xeiam.xchange.btce.v3.BTCEUtils.getPair(currencyPair));
    }
    else {
      bTCETrades = getBTCETrades(pairs, numberOfItems).getTrades(com.xeiam.xchange.btce.v3.BTCEUtils.getPair(currencyPair));
    }

    return BTCEAdapters.adaptTrades(bTCETrades, currencyPair);
  }

}
