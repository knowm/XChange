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
package com.xeiam.xchange.examples.cexio.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.cexio.dto.trade.CexIOOrder;
import com.xeiam.xchange.cexio.service.polling.CexIOTradeServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.cexio.CexIODemoUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * Author: brox Since: 2/6/14
 */

public class TradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CexIODemoUtils.createExchange();
    PollingTradeService tradeService = exchange.getPollingTradeService();

    generic(tradeService);
    raw((CexIOTradeServiceRaw) tradeService);

  }

  private static void generic(PollingTradeService tradeService) throws NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder(Order.OrderType.BID, BigDecimal.ONE, new CurrencyPair(Currencies.GHs, Currencies.BTC), "", null, new BigDecimal("0.00015600"));
    System.out.println("Trying to place: " + limitOrder);
    String orderId = "0";
    try {
      orderId = tradeService.placeLimitOrder(limitOrder);
      System.out.println("New Limit Order ID: " + orderId);
    } catch (ExchangeException e) {
      System.out.println(e);
    }
    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(orderId);
    System.out.println("Canceling order id=" + orderId + " returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void raw(CexIOTradeServiceRaw tradeService) throws IOException {

    List<CexIOOrder> openOrders = tradeService.getCexIOOpenOrders(new CurrencyPair("NMC", "BTC"));
    System.out.println(openOrders);
  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
  }
}
