/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.bitbay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTicker;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * @author kpysniak
 */
public class BitbayAdapters {

  /**
   * Singleton
   */
  private BitbayAdapters() {

  }

  /**
   * Adapts a BitbayTicker to a Ticker Object
   * 
   * @param bitbayTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BitbayTicker bitbayTicker, CurrencyPair currencyPair) {

    BigDecimal ask = bitbayTicker.getAsk();
    BigDecimal bid = bitbayTicker.getBid();
    BigDecimal high = bitbayTicker.getMax();
    BigDecimal low = bitbayTicker.getMin();
    BigDecimal volume = bitbayTicker.getVolume();
    BigDecimal last = bitbayTicker.getLast();
    Date timestamp = new Date();

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();
  }

  /**
   * @param orders
   * @param orderType
   * @param currencyPair
   * @return
   */
  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, new Date(), order[0]));
    }

    return limitOrders;
  }

  /**
   * @param bitbayOrderBook
   * @param currencyPair
   * @return
   */
  public static OrderBook adaptOrderBook(BitbayOrderBook bitbayOrderBook, CurrencyPair currencyPair) {

    OrderBook orderBook =
        new OrderBook(new Date(), transformArrayToLimitOrders(bitbayOrderBook.getAsks(), OrderType.ASK, currencyPair), transformArrayToLimitOrders(bitbayOrderBook.getBids(), OrderType.BID,
            currencyPair));

    return orderBook;
  }

  /**
   * @param bitbayTrades
   * @param currencyPair
   * @return
   */
  public static Trades adaptTrades(BitbayTrade[] bitbayTrades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<Trade>();

    for (BitbayTrade bitbayTrade : bitbayTrades) {

      Trade trade = new Trade(null, bitbayTrade.getAmount(), currencyPair, bitbayTrade.getPrice(), new Date(bitbayTrade.getDate()), bitbayTrade.getTid());

      tradeList.add(trade);
    }

    Trades trades = new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
    return trades;
  }

}
