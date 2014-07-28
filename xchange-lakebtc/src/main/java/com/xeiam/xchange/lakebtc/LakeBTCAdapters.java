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
package com.xeiam.xchange.lakebtc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTicker;

/**
 * @author kpysniak
 */
public class LakeBTCAdapters {

  /**
   * Singleton
   */
  private LakeBTCAdapters() {

  }

  public static Ticker adaptTicker(LakeBTCTicker lakeBTCTicker, CurrencyPair currencyPair) {

    BigDecimal ask = lakeBTCTicker.getAsk();
    BigDecimal bid = lakeBTCTicker.getBid();
    BigDecimal high = lakeBTCTicker.getHigh();
    BigDecimal low = lakeBTCTicker.getLow();
    BigDecimal last = lakeBTCTicker.getLast();
    BigDecimal volume = lakeBTCTicker.getVolume();
    Date timestamp = new Date();

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withLast(last).withTimestamp(timestamp).withVolume(volume).build();
  }

  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, new Date(), order[0]));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(LakeBTCOrderBook lakeBTCOrderBook, CurrencyPair currencyPair) {

    OrderBook orderBook =
        new OrderBook(new Date(), transformArrayToLimitOrders(lakeBTCOrderBook.getAsks(), OrderType.ASK, currencyPair), transformArrayToLimitOrders(lakeBTCOrderBook.getBids(), OrderType.BID,
            currencyPair));

    return orderBook;
  }

}
