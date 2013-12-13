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
package com.xeiam.xchange.campbx;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.campbx.dto.marketdata.CampBXOrderBook;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXTicker;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * Various adapters for converting from CampBX DTOs to XChange DTOs
 */
public final class CampBXAdapters {

  /**
   * CampBXOrderBook to a OrderBook Object
   * 
   * @param orderBook
   * @param currency
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @return
   */
  public static OrderBook adaptOrders(CampBXOrderBook orderBook, String currency, String tradableIdentifier) {

    List<LimitOrder> asks = createOrders(tradableIdentifier, currency, Order.OrderType.ASK, orderBook.getAsks());
    List<LimitOrder> bids = createOrders(tradableIdentifier, currency, Order.OrderType.BID, orderBook.getBids());
    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> createOrders(String tradableIdentifier, String currency, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(tradableIdentifier, currency, ask, orderType));
    }
    return limitOrders;
  }

  private static LimitOrder createOrder(String tradableIdentifier, String currency, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), tradableIdentifier, currency, "", null, BigMoney.of(CurrencyUnit.of("USD"), priceAndAmount.get(0)));
  }

  private static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  /**
   * Adapts a CampBXTicker to a Ticker Object
   * 
   * @param campbxTicker
   * @param currency
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @return
   */
  public static Ticker adaptTicker(CampBXTicker campbxTicker, String currency, String tradableIdentifier) {

    BigMoney last = MoneyUtils.parse(currency + " " + campbxTicker.getLast());
    BigMoney bid = MoneyUtils.parse(currency + " " + campbxTicker.getBid());
    BigMoney ask = MoneyUtils.parse(currency + " " + campbxTicker.getAsk());

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).build();

  }

}
