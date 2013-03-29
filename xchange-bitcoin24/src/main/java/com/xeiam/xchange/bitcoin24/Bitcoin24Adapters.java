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
package com.xeiam.xchange.bitcoin24;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;

import com.xeiam.xchange.bitcoin24.dto.marketdata.Bitcoin24Ticker;
import com.xeiam.xchange.bitcoin24.dto.marketdata.Bitcoin24Trade;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Bitcoin24 DTOs to XChange DTOs
 */
public final class Bitcoin24Adapters {

  /**
   * private Constructor
   */
  private Bitcoin24Adapters() {

  }

  /**
   * Adapts a Bitcoin24Order to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String tradableIdentifier, String currency, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigMoney limitPrice;
    limitPrice = MoneyUtils.parse(currency + " " + price);

    return new LimitOrder(orderType, amount, tradableIdentifier, currency, limitPrice);

  }

  /**
   * Adapts a List of Bitcoin24Orders to a List of LimitOrders
   * 
   * @param Bitcoin24Orders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> Bitcoin24Orders, String tradableIdentifier, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] bitcoin24Order : Bitcoin24Orders) {
      // Bid orderbook is reversed order. Insert at index 0 instead of
      // appending
      if (orderType.equalsIgnoreCase("bid")) {
        limitOrders.add(0, adaptOrder(bitcoin24Order[1], bitcoin24Order[0], tradableIdentifier, currency, orderType, id));
      } else {
        limitOrders.add(adaptOrder(bitcoin24Order[1], bitcoin24Order[0], tradableIdentifier, currency, orderType, id));
      }
    }

    return limitOrders;
  }

  /**
   * Adapts a Bitcoin24Trade to a Trade Object
   * 
   * @param Bitcoin24Trade A Bitcoin24 trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(Bitcoin24Trade Bitcoin24Trade, String tradableIdentifier, String currency) {

    OrderType orderType = Bitcoin24Trade.equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = Bitcoin24Trade.getAmount();
    BigMoney price = MoneyUtils.parse(currency + " " + Bitcoin24Trade.getPrice());
    Date date = DateUtils.fromMillisUtc(Bitcoin24Trade.getDate() * 1000L);

    return new Trade(orderType, amount, tradableIdentifier, currency, price, date);
  }

  /**
   * Adapts a Bitcoin24Trade[] to a Trades Object
   * 
   * @param BTCETrades The Bitcoin24 trade data
   * @return The trades
   */
  public static Trades adaptTrades(Bitcoin24Trade[] bitcoin24TTrades, String tradableIdentifier, String currency) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (Bitcoin24Trade Bitcoin24Trade : bitcoin24TTrades) {
      // Date is reversed order. Insert at index 0 instead of appending
      tradesList.add(adaptTrade(Bitcoin24Trade, tradableIdentifier, currency));
    }
    return new Trades(tradesList);
  }

  /**
   * Adapts a Bitcoin24Ticker to a Ticker Object
   * 
   * @param bitcoin24Ticker
   * @return
   */
  public static Ticker adaptTicker(Bitcoin24Ticker bitcoin24Ticker, String tradableIdentifier, String currency) {

    BigMoney last = MoneyUtils.parse(currency + " " + bitcoin24Ticker.getLast());
    BigMoney bid = MoneyUtils.parse(currency + " " + bitcoin24Ticker.getBuy());
    BigMoney ask = MoneyUtils.parse(currency + " " + bitcoin24Ticker.getSell());
    BigDecimal volume = bitcoin24Ticker.getVol();

    // Due to low USD volume, high/low return null and causing NullPointerException in MoneyUtils
    // If null, set to 0 to prevent exception
    BigMoney high;
    BigMoney low;
    if (bitcoin24Ticker.getLow() == null || bitcoin24Ticker.getHigh() == null) {
      high = MoneyUtils.parse(currency + " " + 0);
      low = MoneyUtils.parse(currency + " " + 0);
    } else {
      high = MoneyUtils.parse(currency + " " + bitcoin24Ticker.getHigh());
      low = MoneyUtils.parse(currency + " " + bitcoin24Ticker.getLow());
    }

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).build();
  }

}
