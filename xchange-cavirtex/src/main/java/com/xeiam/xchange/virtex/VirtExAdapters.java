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
package com.xeiam.xchange.virtex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.time.DateTime;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.utils.MoneyUtils;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExOrder;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTrade;

/**
 * Various adapters for converting from VirtEx DTOs to XChange DTOs
 */
public class VirtExAdapters {

  /**
   * Adapts a VirtExOrder to a LimitOrder
   * 
   * @param virtExOrder
   * @param currency
   * @param orderTypeString
   * @return
   */
  public static LimitOrder adaptOrder(double amount, double price, String currency, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal tradeableAmount = (new BigDecimal(amount));
    String tradableIdentifier = Currencies.BTC;
    String transactionCurrency = currency;
    BigMoney limitPrice = MoneyUtils.parseFiat(currency + " " + price);

    LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, tradableIdentifier, transactionCurrency, limitPrice);

    return limitOrder;

  }

  /**
   * Adapts a List of MtGoxOrders to a List of LimitOrders
   * 
   * @param mtGoxOrders
   * @param currency
   * @param orderType
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<VirtExOrder> virtExOrders, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (VirtExOrder virtExOrder : virtExOrders) {
      limitOrders.add(adaptOrder(virtExOrder.getAmount(), virtExOrder.getPrice(), currency, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a VirtExTrade to a Trade Object
   * 
   * @param VirtExTrade
   * @return
   */
  public static Trade adaptTrade(VirtExTrade virtExTrade) {

    OrderType orderType = virtExTrade.equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = new BigDecimal(virtExTrade.getAmount());
    // BigMoney price = MoneyUtils.parseFiat("CAD" + VirtExTrade.getPrice());
    BigMoney price = VirtExUtils.getPrice("CAD", virtExTrade.getPrice());

    DateTime dateTime = DateUtils.fromMillisUtc((long) virtExTrade.getDate() * 1000L);

    return new Trade(orderType, amount, null, "CAD", price, dateTime);
  }

  /**
   * Adapts a MtGoxTrade[] to a Trades Object
   * 
   * @param mtGoxTrades
   * @return
   */
  public static Trades adaptTrades(VirtExTrade[] virtexTrades) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (int i = 0; i < virtexTrades.length; i++) {

      tradesList.add(adaptTrade(virtexTrades[i]));
    }
    return new Trades(tradesList);
  }

  public static String getPriceString(BigMoney price) {

   
      return price.getAmount().stripTrailingZeros().toPlainString();
  }

  public static Ticker adaptTicker(VirtExTicker virtExTicker) {

    BigMoney last = MoneyUtils.parseFiat("CAD" + " " + virtExTicker.getLast());
    //BigMoney bid = MoneyUtils.parseFiat("CAD" + " " + virtExTicker.getBid()); // Values not available via Virtex Ticker
    //BigMoney ask = MoneyUtils.parseFiat("CAD" + " " + virtExTicker.getAsk()); // Values not available via Virtex Ticker
    BigMoney high = MoneyUtils.parseFiat("CAD" + " " + virtExTicker.getHigh());
    BigMoney low = MoneyUtils.parseFiat("CAD" + " " + virtExTicker.getLow());
    BigDecimal volume = new BigDecimal(virtExTicker.getVolume());

    //return new Ticker("CAD", last, bid, ask, high, low, volume);
    return new Ticker("CAD", last, null, null, high, low, volume);
  }

}
