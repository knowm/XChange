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
package com.xeiam.xchange.examples.kraken.trading;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.examples.kraken.KrakenExampleUtils;
import com.xeiam.xchange.kraken.service.polling.KrakenTradeServiceRaw;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * Test placing a limit order at Kraken.
 */
public class CancelOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    PollingTradeService tradeService = krakenExchange.getPollingTradeService();

    System.out.println("Open Orders: " + tradeService.getOpenOrders().toString());

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((OrderType.BID), BigDecimal.ONE, "BTC", "LTC", "", null, MoneyUtils.parse("LTC 1.25"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    System.out.println("Open Orders: " + tradeService.getOpenOrders().toString());

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);
    // Cancel the added order
    cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling second time  returned " + cancelResult);
    System.out.println("Open Orders: " + tradeService.getOpenOrders().toString());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getPollingTradeService();

    System.out.println("Open Orders: " + tradeService.getKrakenOpenOrders().toString());

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((OrderType.BID), BigDecimal.ONE, "BTC", "LTC", "", null, MoneyUtils.parse("LTC 1.25"));
    String limitOrderReturnValue = tradeService.placeKrakenLimitOrder(limitOrder).getTransactionId();
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    System.out.println("Open Orders: " + tradeService.getKrakenOpenOrders().toString());

    // Cancel the added order
    int cancelResult = tradeService.cancelKrakenOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);
    // Cancel the added order
    cancelResult = tradeService.cancelKrakenOrder(limitOrderReturnValue);
    System.out.println("Canceling second time  returned " + cancelResult);
    System.out.println("Open Orders: " + tradeService.getKrakenOpenOrders().toString());

  }
}
