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
import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCE;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.BTCEUtils;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepthWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;

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
//    btce = RestProxyFactory.createProxy(BTCE.class, exchangeSpecification.getSslUri());
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency, Object... args) throws IOException {

    verify(tradableIdentifier, currency);

    List<CurrencyPair> pairs = new ArrayList<CurrencyPair>(1);
    pairs.add(new CurrencyPair(tradableIdentifier, currency));

    BTCETickerWrapper btceTickerWrapper = getBTCETicker(pairs);

    // Adapt to XChange DTOs
    return BTCEAdapters.adaptTicker(btceTickerWrapper.getTicker(tradableIdentifier, currency), tradableIdentifier, currency);
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
  public OrderBook getOrderBook(String tradableIdentifier, String currency, Object... args) throws IOException {

    verify(tradableIdentifier, currency);

    List<CurrencyPair> pairs = new ArrayList<CurrencyPair>(1);
    pairs.add(new CurrencyPair(tradableIdentifier, currency));

    BTCEDepthWrapper btceDepthWrapper = null;

    if (args.length > 0) {
      Object arg = args[0];
      if (!(arg instanceof Integer) || ((Integer) arg < 1) || ((Integer) arg > FULL_SIZE)) {
        throw new ExchangeException("Orderbook size argument must be an Integer in the range: (1, 2000)!");
      }
      else {
        btceDepthWrapper = getBTCEDepth(pairs, (Integer) arg);
      }
    }
    else { // default to full orderbook
      btceDepthWrapper = getBTCEDepth(pairs, FULL_SIZE);
    }

    // Adapt to XChange DTOs
    List<LimitOrder> asks = BTCEAdapters.adaptOrders(btceDepthWrapper.getDepth(tradableIdentifier, currency).getAsks(), tradableIdentifier, currency, "ask", "");
    List<LimitOrder> bids = BTCEAdapters.adaptOrders(btceDepthWrapper.getDepth(tradableIdentifier, currency).getBids(), tradableIdentifier, currency, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  /**
   * Get recent trades from exchange
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @param args Optional arguments. This implementation assumes
   *          args[0] is integer value limiting number of trade items to get.
   *          -1 or missing -> use default 2000 max fetch value
   *          int from 1 to 2000 -> use API v.3 to get corresponding number of trades
   * @return Trades object
   * @throws IOException
   */
  @Override
  public Trades getTrades(String tradableIdentifier, String currency, Object... args) throws IOException {

    verify(tradableIdentifier, currency);

    List<CurrencyPair> pairs = new ArrayList<CurrencyPair>(1);
    pairs.add(new CurrencyPair(tradableIdentifier, currency));

    int numberOfItems = -1;
    try {
      numberOfItems = (Integer) args[0];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no arg given.
    }
    BTCETrade[] bTCETrades = null;

    if (numberOfItems == -1) {
      bTCETrades = getBTCETrades(pairs, FULL_SIZE).getTrades(tradableIdentifier.toLowerCase(), currency.toLowerCase());
    }
    else {
      bTCETrades = getBTCETrades(pairs, numberOfItems).getTrades(tradableIdentifier.toLowerCase(), currency.toLowerCase());
    }

    return BTCEAdapters.adaptTrades(bTCETrades, tradableIdentifier, currency);
  }

  /**
   * Verify that both currencies can make valid pair
   * 
   * @param tradableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency
   */
  private void verify(String tradableIdentifier, String currency) throws IOException {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(BTCEUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return BTCEUtils.CURRENCY_PAIRS;
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws IOException {

    BTCEExchangeInfo bTCEExchangeInfo = getBTCEInfo();
    return BTCEAdapters.adaptExchangeInfo(bTCEExchangeInfo);
  }

}
