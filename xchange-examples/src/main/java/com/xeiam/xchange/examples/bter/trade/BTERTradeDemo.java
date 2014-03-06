/**
Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do
so, subject to the following conditions:
 *
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
 *
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.xeiam.xchange.examples.bter.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bter.dto.BTEROrderType;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrder;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrders;
import com.xeiam.xchange.bter.dto.trade.BTEROrderStatus;
import com.xeiam.xchange.bter.service.polling.BTERPollingTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.bter.BTERDemoUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BTERTradeDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange exchange = BTERDemoUtils.createExchange();
    PollingTradeService tradeService = exchange.getPollingTradeService();

    generic(tradeService);
    raw((BTERPollingTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException, InterruptedException {

    LimitOrder limitOrder = new LimitOrder(OrderType.ASK, new BigDecimal("0.384"), CurrencyPair.LTC_BTC, "", null, new BigDecimal("0.0265"));
    String orderId = tradeService.placeLimitOrder(limitOrder);
    System.out.println(orderId);  // Returned order id is currently broken for BTER, rely on open orders instead for demo :(

    Thread.sleep(2000);  // wait for BTER's back-end to propagate the order
    
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    List<LimitOrder> openOrdersList = openOrders.getOpenOrders();
    if (!openOrdersList.isEmpty()) {
      String existingOrderId = openOrdersList.get(0).getId();
      
      boolean isCancelled = tradeService.cancelOrder(existingOrderId);
      System.out.println(isCancelled);
    }
    
    Thread.sleep(2000); // wait for BTER's back-end to propagate the cancelled order
    
    openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);
  }

  private static void raw(BTERPollingTradeServiceRaw tradeService) throws IOException, InterruptedException {

    String orderId = tradeService.placeBTERLimitOrder(CurrencyPair.LTC_BTC, BTEROrderType.SELL, new BigDecimal("0.0265"), new BigDecimal("0.384"));
    System.out.println(orderId); // Returned order id is currently broken for BTER, rely on open orders instead for demo :(

    Thread.sleep(2000);  // wait for BTER's back-end to propagate the order
    
    BTEROpenOrders openOrders = tradeService.getBTEROpenOrders();
    System.out.println(openOrders);

    List<BTEROpenOrder> openOrdersList = openOrders.getOrders();
    if (!openOrdersList.isEmpty()) {
      String existingOrderId = openOrdersList.get(0).getId();
      BTEROrderStatus orderStatus = tradeService.getBTEROrderStatus(existingOrderId);
      System.out.println(orderStatus);
      
      boolean isCancelled = tradeService.cancelOrder(existingOrderId);
      System.out.println(isCancelled);
    }
       
    Thread.sleep(2000); // wait for BTER's back-end to propagate the cancelled order
    
    openOrders = tradeService.getBTEROpenOrders();
    System.out.println(openOrders);
     
  }
}
