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
package com.xeiam.xchange.virtex.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTrade;

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
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderTypeString, String id) {

    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    return new LimitOrder(orderType, amount, currencyPair, id, null, price);

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
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> virtexOrders, CurrencyPair currencyPair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] virtexOrder : virtexOrders) {
      limitOrders.add(adaptOrder(virtexOrder[1], virtexOrder[0], currencyPair, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a VirtExTrade to a Trade Object
   * 
   * @param virtExTrade A VirtEx trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(VirtExTrade virtExTrade, CurrencyPair currencyPair) {

    BigDecimal amount = virtExTrade.getAmount();
    Date date = DateUtils.fromMillisUtc((long) virtExTrade.getDate() * 1000L);
    final String tradeId = String.valueOf(virtExTrade.getTid());
    return new Trade(null, amount, currencyPair, virtExTrade.getPrice(), date, tradeId);
  }

  /**
   * Adapts a VirtExTrade[] to a Trades Object
   * 
   * @param virtExTrades The VirtEx trade data
   * @return The trades
   */
  public static Trades adaptTrades(List<VirtExTrade> virtExTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (VirtExTrade virtexTrade : virtExTrades) {
      tradesList.add(adaptTrade(virtexTrade, currencyPair));
    }
    return new Trades(tradesList, TradeSortType.SortByID);
  }

  /**
   * Adapts a VirtExTicker to a Ticker Object
   * 
   * @param virtExTicker
   * @return
   */
  public static Ticker adaptTicker(VirtExTicker virtExTicker, CurrencyPair currencyPair) {

    BigDecimal last = virtExTicker.getLast();
    BigDecimal high = virtExTicker.getHigh();
    BigDecimal low = virtExTicker.getLow();
    BigDecimal volume = virtExTicker.getVolume();
    BigDecimal buy = virtExTicker.getBuy();
    BigDecimal sell = virtExTicker.getSell();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withHigh(high).withLow(low).withAsk(sell).withBid(buy).withVolume(volume).build();
  }

}
