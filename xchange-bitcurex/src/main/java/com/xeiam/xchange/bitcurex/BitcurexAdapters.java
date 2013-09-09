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
package com.xeiam.xchange.bitcurex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;

import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Bitcurex DTOs to XChange DTOs
 */
public final class BitcurexAdapters {

  /**
   * private Constructor
   */
  private BitcurexAdapters() {

  }

  /**
   * Adapts a BitcurexOrder to a LimitOrder
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
   * Adapts a List of bitcurexOrders to a List of LimitOrders
   * 
   * @param bitcurexOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> bitcurexOrders, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] bitcurexOrder : bitcurexOrders) {
      limitOrders.add(adaptOrder(bitcurexOrder[1], bitcurexOrder[0], currency, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a BitcurexTrade to a Trade Object
   * 
   * @param bitcurexTrade A Bitcurex trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BitcurexTrade bitcurexTrade, String currency, String tradableIdentifier) {

    BigDecimal amount = bitcurexTrade.getAmount();
    BigMoney price = MoneyUtils.parse(currency + " " + bitcurexTrade.getPrice());
    Date date = DateUtils.fromMillisUtc((long) bitcurexTrade.getDate() * 1000L);

    return new Trade(null, amount, tradableIdentifier, currency, price, date, bitcurexTrade.getTid());
  }

  /**
   * Adapts a BitcurexTrade[] to a Trades Object
   * 
   * @param bitcurexTrades The Bitcurex trade data
   * @return The trades
   */
  public static Trades adaptTrades(BitcurexTrade[] bitcurexTrades, String currency, String tradableIdentifier) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (BitcurexTrade bitcurexTrade : bitcurexTrades) {
      tradesList.add(adaptTrade(bitcurexTrade, currency, tradableIdentifier));
    }
    return new Trades(tradesList);
  }

  public static String getPriceString(BigMoney price) {

    return price.getAmount().stripTrailingZeros().toPlainString();
  }

  /**
   * Adapts a BitcurexTicker to a Ticker Object
   * 
   * @param bitcurexTicker
   * @return
   */
  public static Ticker adaptTicker(BitcurexTicker bitcurexTicker, String currency, String tradableIdentifier) {

    BigMoney last = MoneyUtils.parse(currency + " " + bitcurexTicker.getLast());
    BigMoney high = MoneyUtils.parse(currency + " " + bitcurexTicker.getHigh());
    BigMoney low = MoneyUtils.parse(currency + " " + bitcurexTicker.getLow());
    BigMoney buy = MoneyUtils.parse(currency + " " + bitcurexTicker.getBuy());
    BigMoney sell = MoneyUtils.parse(currency + " " + bitcurexTicker.getSell());
    BigDecimal volume = bitcurexTicker.getVol();

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withHigh(high).withLow(low).withBid(buy).withAsk(sell).withVolume(volume).build();
  }

}
