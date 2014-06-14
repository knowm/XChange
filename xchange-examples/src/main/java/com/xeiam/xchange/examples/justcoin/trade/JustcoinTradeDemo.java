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
package com.xeiam.xchange.examples.justcoin.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.examples.justcoin.JustcoinDemoUtils;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinTrade;
import com.xeiam.xchange.justcoin.service.polling.JustcoinTradeServiceRaw;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class JustcoinTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange justcoinExchange = JustcoinDemoUtils.createExchange();

    generic(justcoinExchange);
    raw(justcoinExchange);
  }

  private static void generic(Exchange justcoinExchange) throws IOException {

    PollingTradeService genericTradeService = justcoinExchange.getPollingTradeService();

    Trades trades = genericTradeService.getTradeHistory();
    System.out.println("Trade History: " + trades);

    System.out.println("Open Orders: " + genericTradeService.getOpenOrders());

    // Place an ask limit order to sell BTC priced in LTC
    LimitOrder limitOrder = new LimitOrder(OrderType.ASK, new BigDecimal("0.01"), CurrencyPair.BTC_LTC, "", null, new BigDecimal("60"));
    String orderId = genericTradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order Id: " + orderId);

    System.out.println("Open Orders: " + genericTradeService.getOpenOrders());

    // Cancel the added order
    boolean cancelResult = genericTradeService.cancelOrder(orderId);
    System.out.println("Canceling order " + orderId + " returned " + cancelResult);

    System.out.println("Open Orders: " + genericTradeService.getOpenOrders());
  }

  private static void raw(Exchange justcoinExchange) throws IOException {

    JustcoinTradeServiceRaw justcoinSpecificTradeService = (JustcoinTradeServiceRaw) justcoinExchange.getPollingTradeService();

    JustcoinTrade[] trades = justcoinSpecificTradeService.getOrderHistory();
    System.out.println("Trade History: " + trades);

    System.out.println("Open Orders: " + Arrays.toString(justcoinSpecificTradeService.getOrders()));

    // Place a bid limit order to buy BTC priced in LTC
    LimitOrder limitOrder = new LimitOrder(OrderType.BID, new BigDecimal("0.01"), CurrencyPair.BTC_LTC, "", null, new BigDecimal("10"));
    String orderId = justcoinSpecificTradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order Id: " + orderId);

    System.out.println("Open Orders: " + Arrays.toString(justcoinSpecificTradeService.getOrders()));

    // Cancel the added order
    boolean cancelResult = justcoinSpecificTradeService.cancelOrder(orderId);
    System.out.println("Canceling order " + orderId + " returned " + cancelResult);

    System.out.println("Open Orders: " + Arrays.toString(justcoinSpecificTradeService.getOrders()));
  }
}
