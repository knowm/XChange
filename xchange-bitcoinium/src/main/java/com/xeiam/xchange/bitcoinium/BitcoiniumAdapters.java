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

import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.currency.CurrencyPair;
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
  public static Ticker adaptTicker(BitcoiniumTicker bitcoiniumTicker, CurrencyPair currencyPair) {

    BigDecimal last = bitcoiniumTicker.getLast();
    BigDecimal high = bitcoiniumTicker.getHigh();
    BigDecimal low = bitcoiniumTicker.getLow();
    BigDecimal ask = bitcoiniumTicker.getAsk();
    BigDecimal bid = bitcoiniumTicker.getBid();
    BigDecimal volume = bitcoiniumTicker.getVolume();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withHigh(high).withLow(low).withVolume(volume).withAsk(ask).withBid(bid).build();
  }

  /**
   * Adapts a BitcoiniumOrderbook to a OrderBook Object
   * 
   * @param bitcoiniumOrderbook
   * @param CurrencyPair currencyPair (e.g. BTC/USD)
   * @return the XChange OrderBook
   */
  public static OrderBook adaptOrderbook(BitcoiniumOrderbook bitcoiniumOrderbook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, bitcoiniumOrderbook.getAskPriceList(), bitcoiniumOrderbook.getAskVolumeList());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, bitcoiniumOrderbook.getBidPriceList(), bitcoiniumOrderbook.getBidVolumeList());
    Date date = new Date(bitcoiniumOrderbook.getTimestamp()); // Note, this is the timestamp of the piggy-backed Ticker.
    return new OrderBook(date, asks, bids);

  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<BigDecimal> prices, List<BigDecimal> volumes) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (int i = 0; i < prices.size(); i++) {

      LimitOrder limitOrder = new LimitOrder(orderType, volumes.get(i), currencyPair, "", null, prices.get(i));
      limitOrders.add(limitOrder);
    }
    return limitOrders;
  }
}
