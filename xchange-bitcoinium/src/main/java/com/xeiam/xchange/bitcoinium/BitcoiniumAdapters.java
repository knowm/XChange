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
package com.xeiam.xchange.bitcoinium;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * Various adapters for converting from Bitcoinium DTOs to XChange DTOs
 */
public final class BitcoiniumAdapters {

  /**
   * private Constructor
   */
  private BitcoiniumAdapters() {

  }

  /**
   * Adapts a BitcoiniumTicker to a Ticker Object
   * 
   * @param bitcoiniumTicker
   * @return
   */
  public static Ticker adaptTicker(BitcoiniumTicker bitcoiniumTicker, String currency, String tradableIdentifier) {

    BigMoney last = MoneyUtils.parse(currency + " " + bitcoiniumTicker.getLast());
    BigMoney high = MoneyUtils.parse(currency + " " + bitcoiniumTicker.getHigh());
    BigMoney low = MoneyUtils.parse(currency + " " + bitcoiniumTicker.getLow());
    BigMoney ask = MoneyUtils.parse(currency + " " + bitcoiniumTicker.getAsk());
    BigMoney bid = MoneyUtils.parse(currency + " " + bitcoiniumTicker.getBid());
    BigDecimal volume = bitcoiniumTicker.getVolume();

    return TickerBuilder.newInstance().withCurrencyPair(tradableIdentifier).withLast(last).withHigh(high).withLow(low).withVolume(volume).withAsk(ask).withBid(bid).build();
  }

  /**
   * Adapts a BitcoiniumOrderbook to a OrderBook Object
   * 
   * @param bitcoiniumOrderbook
   * @param currency The currency (e.g. USD in BTC/USD)
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @return the XChange OrderBook
   */
  public static OrderBook adaptOrderbook(BitcoiniumOrderbook bitcoiniumOrderbook, String tradableIdentifier, String currency) {

    List<LimitOrder> asks = createOrders(tradableIdentifier, currency, Order.OrderType.ASK, bitcoiniumOrderbook.getAskPriceList(), bitcoiniumOrderbook.getAskVolumeList());
    List<LimitOrder> bids = createOrders(tradableIdentifier, currency, Order.OrderType.BID, bitcoiniumOrderbook.getBidPriceList(), bitcoiniumOrderbook.getBidVolumeList());
    Date date = new Date(bitcoiniumOrderbook.getTimestamp()); // Note, this is the timestamp of the piggy-backed Ticker.
    return new OrderBook(date, asks, bids);

  }

  public static List<LimitOrder> createOrders(String tradableIdentifier, String currency, Order.OrderType orderType, List<BigDecimal> prices, List<BigDecimal> volumes) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (int i = 0; i < prices.size(); i++) {

      LimitOrder limitOrder = new LimitOrder(orderType, volumes.get(i), tradableIdentifier, currency, "", null, BigMoney.of(CurrencyUnit.getInstance(currency), prices.get(i)));
      limitOrders.add(limitOrder);
    }
    return limitOrders;
  }
}
