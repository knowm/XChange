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
package com.xeiam.xchange.examples.btctrade.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.BTCTradeExchange;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradeOrder;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeTradeServiceRaw;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class TradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    // API key with Read Only Permission or All Permissions.
    String publicKey = args[0];
    String privateKey = args[1];

    ExchangeSpecification spec = new ExchangeSpecification(BTCTradeExchange.class);
    spec.setApiKey(publicKey);
    spec.setSecretKey(privateKey);

    Exchange btcTrade = ExchangeFactory.INSTANCE.createExchange(spec);
    generic(btcTrade);
    raw(btcTrade);
  }

  private static void generic(Exchange exchange) throws IOException {

    PollingTradeService tradeService = exchange.getPollingTradeService();

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open orders: " + openOrders);

    Trades trades = tradeService.getTradeHistory();
    System.out.println("Trades: " + trades);
  }

  private static void raw(Exchange exchange) {

    BTCTradeTradeServiceRaw tradeService = (BTCTradeTradeServiceRaw) exchange.getPollingTradeService();

    BTCTradeOrder[] orders = tradeService.getBTCTradeOrders(0, "open");
    System.out.println("Open orders: " + orders.length);

    BTCTradeOrder order = tradeService.getBTCTradeOrder("1");
    System.out.println("Order status: " + order.getStatus());
  }

}
