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
package com.xeiam.xchange.bittrex.v1.service.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bittrex.v1.BittrexExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BittrexTradeTest {

  public static void main(String[] args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setApiKey("void-b7880004add9b32363e28eb7f48");
    specification.setSecretKey("void-6136d444637b4e1a65859dbe5a1");
    exchange.applySpecification(specification);

    PollingTradeService tradeService = exchange.getPollingTradeService();

    CurrencyPair pair = new CurrencyPair("ZET", "BTC");
    LimitOrder limitOrder = new LimitOrder.Builder(OrderType.BID, pair).setLimitPrice(new BigDecimal("0.00001000")).setTradableAmount(new BigDecimal("100")).build();

    try {
      String uuid = tradeService.placeLimitOrder(limitOrder);
      System.out.println("Order successfully placed. ID=" + uuid);

      Thread.sleep(7000); // wait for order to propagate

      System.out.println();
      System.out.println(tradeService.getOpenOrders());

      System.out.println("Attempting to cancel order " + uuid);
      boolean cancelled = tradeService.cancelOrder(uuid);

      if (cancelled) {
        System.out.println("Order successfully canceled.");
      }
      else {
        System.out.println("Order not successfully canceled.");
      }

      Thread.sleep(7000); // wait for cancellation to propagate

      System.out.println();
      System.out.println(tradeService.getOpenOrders());

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
