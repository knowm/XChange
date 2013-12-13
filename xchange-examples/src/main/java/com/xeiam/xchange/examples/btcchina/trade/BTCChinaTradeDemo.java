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
package com.xeiam.xchange.examples.btcchina.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.btcchina.BTCChinaExamplesUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitstamp exchange with authentication</li>
 * <li>Enter, review and cancel limit orders</li>
 * </ul>
 */
public class BTCChinaTradeDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange btcchina = BTCChinaExamplesUtils.getExchange();
    PollingTradeService tradeService = btcchina.getPollingTradeService();

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((OrderType.BID), BigDecimal.ONE, "BTC", "CNY", "", null, MoneyUtils.parse("CNY 0.01"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    Thread.sleep(1500);
    OpenOrders openOrders = printOpenOrders(tradeService);

    long result = -1;
    for (LimitOrder order : openOrders.getOpenOrders()) {
      long orderId = Long.parseLong(order.getId());
      if (order.getType() == limitOrder.getType() && order.getLimitPrice().getAmount().compareTo(limitOrder.getLimitPrice().getAmount()) == 0
          && order.getTradableIdentifier() == order.getTradableIdentifier() && order.getTransactionCurrency() == order.getTransactionCurrency() && orderId > result) {
        result = orderId;
      }
    }

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(Long.toString(result));
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static OpenOrders printOpenOrders(PollingTradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
    return openOrders;
  }
}
