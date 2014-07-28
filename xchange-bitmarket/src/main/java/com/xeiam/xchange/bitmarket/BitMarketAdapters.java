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
package com.xeiam.xchange.bitmarket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
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
public class BitMarketAdapters {

  /**
   * Singleton
   */
  private BitMarketAdapters() {

  }

  /**
   * Adapts BitMarket ticker to Ticker.
   * 
   * @param bitMarketTicker
   * @param currencyPair
   * @return
   */
  public static Ticker adaptTicker(BitMarketTicker bitMarketTicker, CurrencyPair currencyPair) {

    BigDecimal bid = bitMarketTicker.getBid();
    BigDecimal ask = bitMarketTicker.getAsk();
    BigDecimal high = bitMarketTicker.getHigh();
    BigDecimal low = bitMarketTicker.getLow();
    BigDecimal volume = bitMarketTicker.getVolume();
    BigDecimal last = bitMarketTicker.getLast();

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).build();
  }

  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, new Date(), order[0]));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(BitMarketOrderBook bitMarketOrderBook, CurrencyPair currencyPair) {

    OrderBook orderBook =
        new OrderBook(new Date(), transformArrayToLimitOrders(bitMarketOrderBook.getAsks(), OrderType.ASK, currencyPair), transformArrayToLimitOrders(bitMarketOrderBook.getBids(), OrderType.BID,
            currencyPair));

    return orderBook;
  }

  public static Trades adaptTrades(BitMarketTrade[] bitMarketTrades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<Trade>();

    for (BitMarketTrade bitMarketTrade : bitMarketTrades) {

      Trade trade = new Trade(OrderType.BID, bitMarketTrade.getAmount(), currencyPair, bitMarketTrade.getPrice(), new Date(bitMarketTrade.getDate()), bitMarketTrade.getTid());

      tradeList.add(trade);
    }

    Trades trades = new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
    return trades;
  }
}
