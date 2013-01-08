/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.time.DateTime;

import com.xeiam.xchange.btce.dto.marketdata.BTCETicker;
import com.xeiam.xchange.btce.dto.marketdata.BTCETrade;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.utils.MoneyUtils;

/**
 * Various adapters for converting from BTCE DTOs to XChange DTOs
 */
public final class BTCEAdapters {

  /**
   * private Constructor
   */
  private BTCEAdapters() {

  }

  /**
   * Adapts a BTCEOrder to a LimitOrder
   * 
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
    limitPrice = MoneyUtils.parseFiat(currency + " " + price);

    return new LimitOrder(orderType, amount, tradableIdentifier, currency, limitPrice);

  }

  /**
   * Adapts a List of BTCEOrders to a List of LimitOrders
   * 
   *
   * @param BTCEOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> BTCEOrders, String tradableIdentifier, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] btceOrder : BTCEOrders) {
      // Bid orderbook is reversed order. Insert at index 0 instead of
      // appending
      if (orderType.equalsIgnoreCase("bid")) {
        limitOrders.add(0, adaptOrder(btceOrder[1], btceOrder[0], tradableIdentifier, currency, orderType, id));
      } else {
        limitOrders.add(adaptOrder(btceOrder[1], btceOrder[0], tradableIdentifier, currency, orderType, id));
      }
    }

    return limitOrders;
  }

  /**
   * Adapts a BTCETrade to a Trade Object
   * 
   * @param BTCETrade A BTCE trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCETrade BTCETrade) {

    OrderType orderType = BTCETrade.equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = BTCETrade.getAmount();
    String currency = BTCETrade.getPriceCurrency();
    BigMoney price = BTCEUtils.getPrice(currency, BTCETrade.getPrice());
    String tradableIdentifier = BTCETrade.getItem();
    DateTime dateTime = DateUtils.fromMillisUtc(BTCETrade.getDate() * 1000L);

    return new Trade(orderType, amount, tradableIdentifier, currency, price, dateTime);
  }

  /**
   * Adapts a BTCETrade[] to a Trades Object
   * 
   * @param BTCETrades The BTCE trade data
   * @return The trades
   */
  public static Trades adaptTrades(BTCETrade[] BTCETrades) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (BTCETrade BTCETrade : BTCETrades) {
      // Date is reversed order. Insert at index 0 instead of appending
      tradesList.add(0, adaptTrade(BTCETrade));
    }
    return new Trades(tradesList);
  }

  /**
   * Adapts a BTCETicker to a Ticker Object
   * 
   * @param BTCETicker
   * @return
   */
  public static Ticker adaptTicker(BTCETicker BTCETicker, String tradableIdentifier, String currency) {

    BigMoney last = MoneyUtils.parseFiat(currency + " " + BTCETicker.getTicker().getLast());
    BigMoney bid = MoneyUtils.parseFiat(currency + " " + BTCETicker.getTicker().getSell());
    BigMoney ask = MoneyUtils.parseFiat(currency + " " + BTCETicker.getTicker().getBuy());
    BigMoney high = MoneyUtils.parseFiat(currency + " " + BTCETicker.getTicker().getHigh());
    BigMoney low = MoneyUtils.parseFiat(currency + " " + BTCETicker.getTicker().getLow());
    BigDecimal volume = BTCETicker.getTicker().getVol();

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).build();
  }

}
