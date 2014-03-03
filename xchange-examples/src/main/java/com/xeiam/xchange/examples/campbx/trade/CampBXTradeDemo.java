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
package com.xeiam.xchange.examples.campbx.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.CampBXExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Camp BX exchange with authentication</li>
 * <li>Enter, review and cancel limit orders</li>
 * </ul>
 */
public class CampBXTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange campbx = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());

    ExchangeSpecification exSpec = campbx.getExchangeSpecification();
    exSpec.setUserName("XChange");
    exSpec.setPassword("The Java API");

    PollingTradeService tradeService = campbx.getPollingTradeService();

    printOpenOrders(tradeService);

    LimitOrder lo = new LimitOrder((OrderType.ASK), new BigDecimal("0.1"), CurrencyPair.BTC_USD, "", null, new BigDecimal("28.99"));
    String rv = tradeService.placeLimitOrder(lo);
    System.out.println("Limit Order return value: " + rv);
    // place a limit sell order
    LimitOrder limitOrder = new LimitOrder((OrderType.ASK), new BigDecimal("0.1"), CurrencyPair.BTC_USD, "", null, new BigDecimal("110"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    // boolean cancelResult = tradeService.cancelOrder("Buy-1234");
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }
}
