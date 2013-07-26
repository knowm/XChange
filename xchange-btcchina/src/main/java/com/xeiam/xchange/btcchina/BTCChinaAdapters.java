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
package com.xeiam.xchange.btcchina;

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
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;

/**
 * Various adapters for converting from BTCChina DTOs to XChange DTOs
 */
public final class BTCChinaAdapters {

  /**
   * private Constructor
   */
  private BTCChinaAdapters() {

  }

  /**
   * Adapts a BTCChinaOrder to a LimitOrder
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
   * Adapts a List of btcchinaOrders to a List of LimitOrders
   * 
   * @param btcchinaOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> btcchinaOrders, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] btcchinaOrder : btcchinaOrders) {
      limitOrders.add(adaptOrder(btcchinaOrder[1], btcchinaOrder[0], currency, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a BTCChinaTrade to a Trade Object
   * 
   * @param btcChinaTrade A BTCChina trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCChinaTrade btcChinaTrade, String currency, String tradableIdentifier) {

    BigDecimal amount = btcChinaTrade.getAmount();
    BigMoney price = MoneyUtils.parse(currency + " " + btcChinaTrade.getPrice());
    Date date = DateUtils.fromMillisUtc((long) btcChinaTrade.getDate() * 1000L);

    return new Trade(null, amount, tradableIdentifier, currency, price, date, btcChinaTrade.getTid());
  }

  /**
   * Adapts a BTCChinaTrade[] to a Trades Object
   * 
   * @param btcchinaTrades The BTCChina trade data
   * @return The trades
   */
  public static Trades adaptTrades(BTCChinaTrade[] btcchinaTrades, String currency, String tradableIdentifier) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (BTCChinaTrade btcchinaTrade : btcchinaTrades) {
      tradesList.add(adaptTrade(btcchinaTrade, currency, tradableIdentifier));
    }
    return new Trades(tradesList);
  }

  public static String getPriceString(BigMoney price) {

    return price.getAmount().stripTrailingZeros().toPlainString();
  }

  /**
   * Adapts a BTCChinaTicker to a Ticker Object
   * 
   * @param btcChinaTicker
   * @return
   */
  public static Ticker adaptTicker(BTCChinaTicker btcChinaTicker, String currency, String tradableIdentifier) {

    BigMoney last = MoneyUtils.parse(currency + " " + btcChinaTicker.getTicker().getLast());
    BigMoney high = MoneyUtils.parse(currency + " " + btcChinaTicker.getTicker().getHigh());
    BigMoney low = MoneyUtils.parse(currency + " " + btcChinaTicker.getTicker().getLow());
    BigMoney buy = MoneyUtils.parse(currency + " " + btcChinaTicker.getTicker().getBuy());
    BigMoney sell = MoneyUtils.parse(currency + " " + btcChinaTicker.getTicker().getSell());
    BigDecimal volume = btcChinaTicker.getTicker().getVol();

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withHigh(high).withLow(low).withBid(buy).withAsk(sell).withVolume(volume).build();
  }

}
