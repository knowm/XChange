/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.btcchina;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.xeiam.xchange.btcchina.dto.trade.BTCChinaTransaction;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trade;


public class BTCChinaAdaptersTest {

  @Test
  public void testAdaptTransaction() {
    BTCChinaTransaction btcChinaTransaction = new BTCChinaTransaction(
      12158242,                        // id
      "sellbtc",                       // type
      new BigDecimal("-0.37460000"),   // btc_amount
      new BigDecimal("0.00000000"),    // ltc_amount
      new BigDecimal("1420.09151800"), // cny_amount
      1402922707L,                     // id
      "btccny"                         // market
      );
    Trade trade = BTCChinaAdapters.adaptTransaction(btcChinaTransaction);

    assertEquals(OrderType.ASK, trade.getType());
    assertEquals(new BigDecimal("0.37460000"), trade.getTradableAmount());
    assertEquals(CurrencyPair.BTC_CNY, trade.getCurrencyPair());
    assertEquals(new BigDecimal("3790.95"), trade.getPrice());
    assertEquals(1402922707000L, trade.getTimestamp().getTime());
    assertEquals("12158242", trade.getId());
  }

}
