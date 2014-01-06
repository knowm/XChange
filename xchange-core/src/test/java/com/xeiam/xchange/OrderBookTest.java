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
package com.xeiam.xchange;

import static org.fest.assertions.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.junit.Before;
import org.junit.Test;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.OrderBookUpdate;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class OrderBookTest {

  private OrderBook orderBook;

  @Before
  public void setUp() throws Exception {

    LimitOrder askOrder = new LimitOrder(OrderType.ASK, BigDecimal.ONE, Currencies.BTC, Currencies.USD, "", null, BigMoney.of(CurrencyUnit.USD, BigDecimal.TEN.add(BigDecimal.ONE)));
    LimitOrder bidOrder = new LimitOrder(OrderType.BID, BigDecimal.ONE, Currencies.BTC, Currencies.USD, "", null, BigMoney.of(CurrencyUnit.USD, BigDecimal.TEN));

    List<LimitOrder> asks = new ArrayList<LimitOrder>(Arrays.asList(askOrder));
    List<LimitOrder> bids = new ArrayList<LimitOrder>(Arrays.asList(bidOrder));
    Date timeStamp = new Date(0);
    orderBook = new OrderBook(timeStamp, asks, bids);

  }

  @Test
  public void testUpdateAddOrder() {

    Date timeStamp = new Date(0);
    OrderBookUpdate lowerBidUpdate =
        new OrderBookUpdate(OrderType.BID, BigDecimal.ONE, Currencies.BTC, Currencies.USD, BigMoney.of(CurrencyUnit.USD, BigDecimal.TEN.subtract(BigDecimal.ONE)), timeStamp, BigDecimal.ONE);
    orderBook.update(lowerBidUpdate);
    assertThat(orderBook.getBids().size()).isEqualTo(2);
  }

  @Test
  public void testUpdateRemoveOrder() {

    Date timeStamp = new Date(0);
    OrderBookUpdate lowerBidUpdate = new OrderBookUpdate(OrderType.BID, BigDecimal.ONE, Currencies.BTC, Currencies.USD, BigMoney.of(CurrencyUnit.USD, BigDecimal.TEN), timeStamp, BigDecimal.ZERO);
    orderBook.update(lowerBidUpdate);
    assertThat(orderBook.getBids().size()).isEqualTo(0);
  }

  @Test
  public void testUpdateAddVolume() {

    Date timeStamp = new Date(0);
    OrderBookUpdate lowerBidUpdate = new OrderBookUpdate(OrderType.BID, BigDecimal.ONE, Currencies.BTC, Currencies.USD, BigMoney.of(CurrencyUnit.USD, BigDecimal.TEN), timeStamp, BigDecimal.TEN);
    orderBook.update(lowerBidUpdate);
    assertThat(orderBook.getBids().size()).isEqualTo(1);
    assertThat(orderBook.getBids().get(0).getTradableAmount()).isEqualTo(BigDecimal.TEN);
  }

  @Test
  public void testDateSame() {

    Date timeStamp = new Date(0);
    OrderBookUpdate lowerBidUpdate = new OrderBookUpdate(OrderType.BID, BigDecimal.ONE, Currencies.BTC, Currencies.USD, BigMoney.of(CurrencyUnit.USD, BigDecimal.TEN), timeStamp, BigDecimal.TEN);
    Date oldDate = orderBook.getTimeStamp();
    orderBook.update(lowerBidUpdate);
    assertThat(orderBook.getTimeStamp()).isEqualTo(oldDate);
  }

  @Test
  public void testDateOther() {

    Date timeStamp = new Date(10);
    OrderBookUpdate lowerBidUpdate = new OrderBookUpdate(OrderType.BID, BigDecimal.ONE, Currencies.BTC, Currencies.USD, BigMoney.of(CurrencyUnit.USD, BigDecimal.TEN), timeStamp, BigDecimal.TEN);
    Date oldDate = orderBook.getTimeStamp();
    orderBook.update(lowerBidUpdate);
    assertThat(orderBook.getTimeStamp()).isAfter(oldDate);
    assertThat(orderBook.getTimeStamp()).isEqualTo(timeStamp);

  }
}
