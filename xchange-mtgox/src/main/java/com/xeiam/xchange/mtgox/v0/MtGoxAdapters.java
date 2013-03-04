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
package com.xeiam.xchange.mtgox.v0;

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
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxTrades;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from MtGox DTOs to XChange DTOs
 */
public final class MtGoxAdapters {

  /**
   * private Constructor
   */
  private MtGoxAdapters() {

  }

  /**
   * Adapts a MtGoxOrder to a LimitOrder
   * 
   * @param amount The amount
   * @param price The price
   * @param currency The currency
   * @param orderTypeString The order type (e.g. "bid")
   * @param id The id
   * @return A generic limit order
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String currency, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    String tradableIdentifier = Currencies.BTC;
    BigMoney limitPrice = MoneyUtils.parse(currency + " " + price);

    return new LimitOrder(orderType, amount, tradableIdentifier, currency, id, limitPrice);

  }

  /**
   * Adapts a List of mtgoxOrders to a List of LimitOrders
   * 
   * @param mtgoxOrders The MtGox orders
   * @param currency The currency
   * @param orderType The order type
   * @param id The id
   * @return A collection of limit orders
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> mtgoxOrders, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    if (mtgoxOrders == null) {
      return limitOrders;
    }

    for (BigDecimal[] mtgoxOrder : mtgoxOrders) {
      limitOrders.add(adaptOrder(mtgoxOrder[1], mtgoxOrder[0], currency, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a MtGoxTrade to a Trade Object
   * 
   * @param mtGoxTrade A MtGox trade
   * @return A generic trade
   */
  public static Trade adaptTrade(MtGoxTrades mtGoxTrade) {

    OrderType orderType = mtGoxTrade.getTrade_type().equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = new BigDecimal(mtGoxTrade.getAmount_int()).divide(new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));
    String tradableIdentifier = mtGoxTrade.getItem();
    String transactionCurrency = mtGoxTrade.getPrice_currency();
    BigMoney price = MtGoxUtils.getPrice(transactionCurrency, mtGoxTrade.getPrice_int());

    Date dateTime = DateUtils.fromMillisUtc(mtGoxTrade.getDate() * 1000L);

    return new Trade(orderType, amount, tradableIdentifier, transactionCurrency, price, dateTime);
  }

  /**
   * Adapts a MtGoxTrade[] to a Trades Object
   * 
   * @param mtGoxTrades MtGox trades
   * @return A collection of trades
   */
  public static Trades adaptTrades(MtGoxTrades[] mtGoxTrades) {

    List<Trade> tradesList = new ArrayList<Trade>();

    if (mtGoxTrades == null) {
      return new Trades(tradesList);
    }

    for (MtGoxTrades mtGoxTrade : mtGoxTrades) {

      tradesList.add(adaptTrade(mtGoxTrade));
    }
    return new Trades(tradesList);
  }

  /**
   * @param mtGoxTicker The MtGox ticker
   * @param tradeableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @return A generic Ticker
   */
  public static Ticker adaptTicker(MtGoxTicker mtGoxTicker, String tradeableIdentifier, String currency) {

    BigMoney last = MoneyUtils.parse(currency + " " + mtGoxTicker.getTicker().getLast());
    BigMoney bid = MoneyUtils.parse(currency + " " + mtGoxTicker.getTicker().getBuy());
    BigMoney ask = MoneyUtils.parse(currency + " " + mtGoxTicker.getTicker().getSell());
    BigMoney high = MoneyUtils.parse(currency + " " + mtGoxTicker.getTicker().getHigh());
    BigMoney low = MoneyUtils.parse(currency + " " + mtGoxTicker.getTicker().getLow());
    BigDecimal volume = mtGoxTicker.getTicker().getVol();

    return TickerBuilder.newInstance().withTradableIdentifier(tradeableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).build();

  }

}
