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
package com.xeiam.xchange.examples.cryptotrade.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeTransactions;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeCancelOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeHistoryQueryParams;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradePlaceOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.cryptotrade.service.polling.CryptoTradeTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.cryptotrade.CryptoTradeExampleUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.utils.CertHelper;

public class CryptoTradeTradeDemo {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange exchange = CryptoTradeExampleUtils.createExchange();
    PollingTradeService accountService = exchange.getPollingTradeService();

    generic(accountService);
    raw((CryptoTradeTradeServiceRaw) accountService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException, InterruptedException {

    Trades tradeHistory = tradeService.getTradeHistory();
    System.out.println(tradeHistory);

    Thread.sleep(4000);

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    Thread.sleep(4000);

    LimitOrder limitOrder = new LimitOrder(OrderType.ASK, new BigDecimal(".01"), CurrencyPair.BTC_USD, null, null, new BigDecimal("1000.00"));
    String orderId = tradeService.placeLimitOrder(limitOrder);
    System.out.println(orderId);

    Thread.sleep(4000);

    openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    Thread.sleep(4000);

    boolean isCancelled = tradeService.cancelOrder(orderId);
    System.out.println(isCancelled);

    Thread.sleep(4000);

    openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

  }

  private static void raw(CryptoTradeTradeServiceRaw tradeService) throws IOException, InterruptedException {

    CryptoTradeHistoryQueryParams params = CryptoTradeHistoryQueryParams.getQueryParamsBuilder().withCount(3).build();

    CryptoTradeTrades tradeHistory = tradeService.getCryptoTradeTradeHistory(params);
    System.out.println(tradeHistory);

    Thread.sleep(4000);

    CryptoTradeTransactions transactions = tradeService.getCryptoTradeTransactionHistory(params);
    System.out.println(transactions);

    Thread.sleep(4000);

    CryptoTradeOrders orders = tradeService.getCryptoTradeOrderHistory(params);
    System.out.println(orders);

    Thread.sleep(4000);

    LimitOrder limitOrder = new LimitOrder(OrderType.ASK, new BigDecimal(".01"), CurrencyPair.BTC_USD, null, null, new BigDecimal("1000.00"));
    CryptoTradePlaceOrderReturn orderReturn = tradeService.placeCryptoTradeLimitOrder(limitOrder);
    System.out.println(orderReturn);

    Thread.sleep(4000);

    orders = tradeService.getCryptoTradeOrderHistory(params);
    System.out.println(orders);

    Thread.sleep(4000);

    CryptoTradeCancelOrderReturn cancelOrderReturn = tradeService.cancelCryptoTradeOrder(orderReturn.getOrderId());
    System.out.println(cancelOrderReturn);

    Thread.sleep(4000);

    orders = tradeService.getCryptoTradeOrderHistory(params);
    System.out.println(orders);
  }
}
