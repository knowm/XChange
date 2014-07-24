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
package com.xeiam.xchange.examples.btcchina.trade;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaTransaction;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeService;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeServiceRaw;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.btcchina.BTCChinaExamplesUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;


/**
 * Demo for {@link BTCChinaTradeService#getTradeHistory(Object...)}.
 *
 * @author Joe Zhou
 */
public class BTCChinaGetTradeHistoryDemo {

  private static Exchange btcchina = BTCChinaExamplesUtils.getExchange();
  private static PollingTradeService tradeService
    = btcchina.getPollingTradeService();
  private static BTCChinaTradeServiceRaw tradeServiceRaw
    = (BTCChinaTradeServiceRaw) tradeService;

  public static void main(String[] args) throws IOException {
    generic();
    raw();
  }

  private static void generic() throws IOException {
    Trades trades = tradeService.getTradeHistory(10);
    System.out.println("Trade count: " +  trades.getTrades().size());
    for (Trade trade : trades.getTrades()) {
      System.out.println(trade);
    }
  }

  private static void raw() throws IOException {
    BTCChinaTransactionsResponse response = tradeServiceRaw.getTransactions("all", 10, null);
    System.out.println("BTCChinaTransactionsResponse: " + response);
    for (BTCChinaTransaction transaction : response.getResult().getTransactions()) {
      System.out.println(ToStringBuilder.reflectionToString(transaction));
    }
  }

}
