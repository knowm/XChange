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
package com.xeiam.xchange.virtex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTrade;

/**
 * Various adapters for converting from VirtEx DTOs to XChange DTOs
 */
public final class VirtExAdapters {

  /**
   * private Constructor
   */
  private VirtExAdapters() {

  }

  /**
   * Adapts a VirtExOrder to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String currency, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    String tradableIdentifier = Currencies.BTC;
    BigMoney limitPrice = MoneyUtils.parse(currency + " " + price);

    return new LimitOrder(orderType, amount, tradableIdentifier, currency, limitPrice);

  }

  /**
   * Adapts a List of virtexOrders to a List of LimitOrders
   * 
   * @param virtexOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> virtexOrders, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] virtexOrder : virtexOrders) {
      limitOrders.add(adaptOrder(virtexOrder[1], virtexOrder[0], currency, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a VirtExTrade to a Trade Object
   * 
   * @param virtExTrade A VirtEx trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(VirtExTrade virtExTrade, String currency, String tradableIdentifier) {

    BigDecimal amount = virtExTrade.getAmount();
    BigMoney price = MoneyUtils.parse(currency + " " + virtExTrade.getPrice());
    Date date = DateUtils.fromMillisUtc((long) virtExTrade.getDate() * 1000L);

    return new Trade(null, amount, tradableIdentifier, currency, price, date);
  }

  /**
   * Adapts a VirtExTrade[] to a Trades Object
   * 
   * @param virtexTrades The VirtEx trade data
   * @return The trades
   */
  public static Trades adaptTrades(VirtExTrade[] virtexTrades, String currency, String tradableIdentifier) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (VirtExTrade virtexTrade : virtexTrades) {
      tradesList.add(adaptTrade(virtexTrade, currency, tradableIdentifier));
    }
    return new Trades(tradesList);
  }

  public static String getPriceString(BigMoney price) {

    return price.getAmount().stripTrailingZeros().toPlainString();
  }

  /**
   * Adapts a VirtExTicker to a Ticker Object
   * 
   * @param virtExTicker
   * @return
   */
  public static Ticker adaptTicker(VirtExTicker virtExTicker, String currency, String tradableIdentifier) {

    BigMoney last = MoneyUtils.parse(currency + " " + virtExTicker.getLast());
    BigMoney high = MoneyUtils.parse(currency + " " + virtExTicker.getHigh());
    BigMoney low = MoneyUtils.parse(currency + " " + virtExTicker.getLow());
    BigDecimal volume = virtExTicker.getVolume();

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withHigh(high).withLow(low).withVolume(volume).build();
  }

}
