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
package com.xeiam.xchange.examples.btcchina.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaMarketDepthOrder;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetMarketDepthResponse;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeServiceRaw;
import com.xeiam.xchange.examples.btcchina.BTCChinaExamplesUtils;

/**
 * Demo for {@link BTCChinaTradeServiceRaw#getMarketDepth(Integer, String)}.
 */
public class BTCChinaGetMarketDepthDemo {

  private static Exchange exchange = BTCChinaExamplesUtils.getExchange();
  private static BTCChinaTradeServiceRaw tradeService = (BTCChinaTradeServiceRaw) exchange.getPollingTradeService();

  public static void main(String[] args) throws IOException {
    getMarketDepth(null, null);
    getMarketDepth(10, null);
    getMarketDepth(null, "LTCCNY");
    getMarketDepth(10, "LTCCNY");
    
  }
  
  private static void getMarketDepth(Integer limit, String market) throws IOException {
    System.out.println(String.format("limit: %d, market: %s", limit, market));

    BTCChinaGetMarketDepthResponse response = tradeService.getMarketDepth(limit, market);
    System.out.println(response);

    for (BTCChinaMarketDepthOrder o : response.getResult().getMarketDepth().getBids()) {
      System.out.println(String.format("Bid %s,%s", o.getPrice(), o.getAmount()));
    }

    for (BTCChinaMarketDepthOrder o : response.getResult().getMarketDepth().getAsks()) {
      System.out.println(String.format("Ask %s,%s", o.getPrice(), o.getAmount()));
    }
  }

}
