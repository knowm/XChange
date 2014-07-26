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
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrder;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrderResponse;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeServiceRaw;
import com.xeiam.xchange.examples.btcchina.BTCChinaExamplesUtils;

/**
 * Demo for {@link BTCChinaTradeServiceRaw#getBTCChinaOrder}.
 *
 * @author Joe Zhou
 */
public class BTCChinaGetOrderDemo {

  static Exchange btcchina = BTCChinaExamplesUtils.getExchange();
  static BTCChinaTradeServiceRaw tradeServiceRaw
    = (BTCChinaTradeServiceRaw) btcchina.getPollingTradeService();

  public static void main(String[] args) throws IOException {
    final long orderId = Long.parseLong(args[0]);
    final String market = args.length > 1 ? args[1] : null;

    BTCChinaGetOrderResponse response;
    if (market == null) {
      response = tradeServiceRaw.getBTCChinaOrder(orderId);
    } else {
      response = tradeServiceRaw.getBTCChinaOrder(orderId, market);
    }
    System.out.println(response);

    BTCChinaOrder order = response.getResult().getOrder();
    System.out.println("OrderID:\t" + order.getId());
    System.out.println("Side:\t" + order.getType());
    System.out.println("Price:\t" + order.getPrice());
    System.out.println("Currency: \t" + order.getCurrency());
    System.out.println("LeavesQty:\t" + order.getAmount());
    System.out.println("OrderQty:\t" + order.getAmountOriginal());
    System.out.println("CumQty:\t" + order.getAmountOriginal().subtract(order.getAmount()));
    System.out.println("Date:\t" + new Date(order.getDate() * 1000));
    System.out.println("Status:\t" + order.getStatus());
  }

}
