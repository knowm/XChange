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
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeService;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeServiceRaw;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.btcchina.BTCChinaExamplesUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * Demo for {@link BTCChinaTradeServiceRaw#getBTCChinaOrders(Boolean, String, Integer, Integer)}.
 * and {@link BTCChinaTradeService#getOpenOrders()}.
 *
 * @author Joe Zhou
 */
public class BTCChinaGetOrdersDemo {

  static Exchange exchange = BTCChinaExamplesUtils.getExchange();
  static PollingTradeService tradeService = exchange.getPollingTradeService();
  static BTCChinaTradeServiceRaw tradeServiceRaw = (BTCChinaTradeServiceRaw) exchange.getPollingTradeService();

  public static void main(String[] args) throws IOException {

    BTCChinaGetOrdersResponse btcCny = tradeServiceRaw.getBTCChinaOrders(true, "BTCCNY", null, null);
    System.out.println(btcCny);

    BTCChinaGetOrdersResponse ltcCny = tradeServiceRaw.getBTCChinaOrders(true, "LTCCNY", null, null);
    System.out.println(ltcCny);

    BTCChinaGetOrdersResponse ltcBtc = tradeServiceRaw.getBTCChinaOrders(true, "LTCBTC", null, null);
    System.out.println(ltcBtc);

    BTCChinaGetOrdersResponse all = tradeServiceRaw.getBTCChinaOrders(true, BTCChinaGetOrdersRequest.ALL_MARKET, null, null);
    System.out.println(all);

    // Generic
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);
  }

}
