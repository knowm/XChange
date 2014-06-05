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
package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexAdapters;
import com.xeiam.xchange.bitfinex.v1.BitfinexUtils;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexDepth;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for Bitfinex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitfinexMarketDataService extends BitfinexMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitfinexMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitfinexAdapters.adaptTicker(getBitfinexTicker(BitfinexUtils.toPairString(currencyPair)), currencyPair);
  }

  /**
   * @param args If two integers are provided, then those count as limit bid and limit ask count
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // According to API docs, default is 50
    int limitBids = 50;
    int limitAsks = 50;

    if (args.length == 2) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer)) {
        throw new ExchangeException("Argument 0 must be an Integer!");
      }
      else {
        limitBids = (Integer) arg0;
      }
      Object arg1 = args[1];
      if (!(arg1 instanceof Integer)) {
        throw new ExchangeException("Argument 1 must be an Integer!");
      }
      else {
        limitAsks = (Integer) arg1;
      }
    }

    BitfinexDepth btceDepth = getBitfinexOrderBook(BitfinexUtils.toPairString(currencyPair), limitBids, limitAsks);

    List<LimitOrder> asks = BitfinexAdapters.adaptOrders(btceDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = BitfinexAdapters.adaptOrders(btceDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  /**
   * @param currencyPair The CurrencyPair for which to query trades.
   * @param args One argument may be supplied which is the timestamp after which trades should be collected.
   *          Trades before this time are not reported. The argument may be of type java.util.Date or
   *          Number (milliseconds since Jan 1, 1970)
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    long lastTradeTime = 0;
    if (args != null && args.length == 1) {
      // parameter 1, if present, is the last trade timestamp
      if (args[0] instanceof Number) {
        Number arg = (Number) args[0];
        lastTradeTime = arg.longValue() / 1000; // divide by 1000 to convert to unix timestamp (seconds)
      }
      else if (args[0] instanceof Date) {
        Date arg = (Date) args[0];
        lastTradeTime = arg.getTime() / 1000; // divide by 1000 to convert to unix timestamp (seconds)
      }
      else
        throw new IllegalArgumentException("Extra argument #1, the last trade time, must be a Date or Long (millisecond timestamp) (was " + args[0].getClass() + ")");
    }
    BitfinexTrade[] trades = getBitfinexTrades(BitfinexUtils.toPairString(currencyPair), lastTradeTime);

    return BitfinexAdapters.adaptTrades(trades, currencyPair);
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws IOException {

    return new ExchangeInfo(BitfinexAdapters.adaptCurrencyPairs(super.getBitfinexSymbols()));
  }

}
