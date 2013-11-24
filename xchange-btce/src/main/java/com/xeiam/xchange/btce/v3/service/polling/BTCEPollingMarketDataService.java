/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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

import si.mazi.rescu.RestProxyFactory;

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
public class BTCEPollingMarketDataService implements PollingMarketDataService {

  protected final BTCE btce;

  /**
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    btce = RestProxyFactory.createProxy(BTCE.class, exchangeSpecification.getSslUri());
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) throws IOException {

    verify(tradableIdentifier, currency);

    BTCETickerWrapper btceTickerWrapper = btce.getTicker(tradableIdentifier.toLowerCase(), currency.toLowerCase(), 1);

    // Adapt to XChange DTOs
    return BTCEAdapters.adaptTicker(btceTickerWrapper.getTicker(tradableIdentifier, currency), tradableIdentifier, currency);
  }

  // TODO possibly allow for passing in custom depth size amounts
  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) throws IOException {

    verify(tradableIdentifier, currency);

    BTCEDepthWrapper btceDepthWrapper = btce.getDepth(tradableIdentifier.toLowerCase(), currency.toLowerCase(), 250, 1);
    // Adapt to XChange DTOs
    List<LimitOrder> asks = BTCEAdapters.adaptOrders(btceDepthWrapper.getDepth(tradableIdentifier, currency).getAsks(), tradableIdentifier, currency, "ask", "");
    List<LimitOrder> bids = BTCEAdapters.adaptOrders(btceDepthWrapper.getDepth(tradableIdentifier, currency).getBids(), tradableIdentifier, currency, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) throws IOException {

    verify(tradableIdentifier, currency);

    BTCEDepthWrapper btceDepthWrapper = btce.getDepth(tradableIdentifier.toLowerCase(), currency.toLowerCase(), 2000, 1);
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

    int numberOfItems = -1;
    try {
      numberOfItems = (Integer) args[0];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no arg given.
    }
    BTCETrade[] bTCETrades = null;

    if (numberOfItems == -1) {
      bTCETrades = btce.getTrades(tradableIdentifier.toLowerCase(), currency.toLowerCase(), 2000, 1).getTrades(tradableIdentifier.toLowerCase(), currency.toLowerCase());
    }
    else {
      bTCETrades = btce.getTrades(tradableIdentifier.toLowerCase(), currency.toLowerCase(), numberOfItems, 1).getTrades(tradableIdentifier.toLowerCase(), currency.toLowerCase());
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

    BTCEExchangeInfo bTCEExchangeInfo = btce.getInfo();
    return BTCEAdapters.adaptExchangeInfo(bTCEExchangeInfo);
  }

}
