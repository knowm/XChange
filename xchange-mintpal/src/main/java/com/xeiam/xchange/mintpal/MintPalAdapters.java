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
package com.xeiam.xchange.mintpal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrder;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;

/**
 * @author jamespedwards42
 */
public class MintPalAdapters {

  public static Collection<CurrencyPair> adaptCurrencyPairs(final List<MintPalTicker> tickers) {

    final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();
    for (final MintPalTicker ticker : tickers)
      currencyPairs.add(adaptCurrencyPair(ticker));
    return currencyPairs;
  }

  public static CurrencyPair adaptCurrencyPair(final MintPalTicker mintPalTicker) {

    return new CurrencyPair(mintPalTicker.getCode(), mintPalTicker.getExchange());
  }

  public static Ticker adaptTicker(final MintPalTicker mintPalTicker) {

    return TickerBuilder.newInstance().withCurrencyPair(adaptCurrencyPair(mintPalTicker)).withAsk(mintPalTicker.getTopAsk()).withBid(mintPalTicker.getTopBid()).withHigh(mintPalTicker.getHigh24Hour())
        .withLow(mintPalTicker.getLow24Hour()).withVolume(mintPalTicker.getVolume24Hour()).withLast(mintPalTicker.getLastPrice()).build();
  }

  public static OrderBook adaptOrderBook(final CurrencyPair currencyPair, final List<MintPalPublicOrders> mintPalOrderBook) {

    final boolean firstIsBids = mintPalOrderBook.get(0).getType().equalsIgnoreCase("buy");
    final List<LimitOrder> bids = firstIsBids ? adaptOrders(currencyPair, OrderType.BID, mintPalOrderBook.get(0)) : adaptOrders(currencyPair, OrderType.BID, mintPalOrderBook.get(1));
    final List<LimitOrder> asks = firstIsBids ? adaptOrders(currencyPair, OrderType.ASK, mintPalOrderBook.get(1)) : adaptOrders(currencyPair, OrderType.ASK, mintPalOrderBook.get(0));

    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> adaptOrders(final CurrencyPair currencyPair, final OrderType orderType, final MintPalPublicOrders mintPalOrders) {

    final List<LimitOrder> orders = new ArrayList<LimitOrder>();
    for (final MintPalPublicOrder mintPalOrder : mintPalOrders.getOrders())
      orders.add(adaptOrder(currencyPair, orderType, mintPalOrder));
    
    return orders;
  }

  public static LimitOrder adaptOrder(final CurrencyPair currencyPair, final OrderType orderType, final MintPalPublicOrder mintPalOrder) {

    return new LimitOrder(orderType, mintPalOrder.getAmount(), currencyPair, null, null, mintPalOrder.getPrice());
  }

  public static Trades adaptPublicTrades(final CurrencyPair currencyPair, final List<MintPalPublicTrade> mintPalTrades) {

    final List<Trade> trades = new ArrayList<Trade>();
    for (final MintPalPublicTrade trade : mintPalTrades)
      trades.add(adaptPublicTrade(currencyPair, trade));

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptPublicTrade(final CurrencyPair currencyPair, final MintPalPublicTrade mintPalTrade) {

    return new Trade(mintPalTrade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK, mintPalTrade.getAmount(), currencyPair, mintPalTrade.getPrice(), mintPalTrade.getTime(), null,
        null);
  }
}
