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
package com.xeiam.xchange.btce.v2.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v2.BTCE;
import com.xeiam.xchange.btce.v2.BTCEAdapters;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEDepth;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETrade;
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
@Deprecated
public class BTCEMarketDataService extends BTCEBasePollingService implements PollingMarketDataService {

  protected final BTCE btce;

  /**
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    btce = RestProxyFactory.createProxy(BTCE.class, exchangeSpecification.getSslUri());
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    BTCETickerWrapper btceTicker = btce.getTicker(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    // Adapt to XChange DTOs
    return BTCEAdapters.adaptTicker(btceTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    BTCEDepth btceDepth = btce.getDepth(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());
    // Adapt to XChange DTOs
    List<LimitOrder> asks = BTCEAdapters.adaptOrders(btceDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = BTCEAdapters.adaptOrders(btceDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  /**
   * Get recent trades from exchange
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @param args Optional arguments. This implementation assumes
   *          args[0] is integer value limiting number of trade items to get.
   *          -1 or missing -> use default output of 150 items from API v.2
   *          int from 1 to 2000 -> use API v.3 to get corresponding number of trades
   * @return Trades object
   * @throws IOException
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    BTCETrade[] BTCETrades = btce.getTrades(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return BTCEAdapters.adaptTrades(BTCETrades);

  }

}
