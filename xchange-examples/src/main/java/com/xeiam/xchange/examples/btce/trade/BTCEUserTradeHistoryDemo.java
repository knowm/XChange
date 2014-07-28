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
package com.xeiam.xchange.examples.btce.trade;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.btce.v3.service.polling.BTCETradeServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.btce.BTCEExamplesUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BTCEUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    // TODO: The APIKey does not have the correct permissions
    Exchange btce = BTCEExamplesUtils.createExchange();
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    PollingTradeService tradeService = exchange.getPollingTradeService();
    Trades trades = null;
    try {
      trades = tradeService.getTradeHistory(null, Currencies.BTC, Currencies.USD);
      System.out.println(trades.toString());
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void raw(Exchange exchange) throws IOException {

    BTCETradeServiceRaw tradeService = (BTCETradeServiceRaw) exchange.getPollingTradeService();
    Map<Long, BTCETradeHistoryResult> trades = null;
    try {
      trades = tradeService.getBTCETradeHistory(null, null, null, null, null, null, null, null);
      for (Map.Entry<Long, BTCETradeHistoryResult> entry : trades.entrySet()) {
        System.out.println("ID: " + entry.getKey() + ", Trade:" + entry.getValue());
      }
      System.out.println(trades.toString());
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }
  }

}
