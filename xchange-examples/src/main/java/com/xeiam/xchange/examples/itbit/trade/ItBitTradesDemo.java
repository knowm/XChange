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
package com.xeiam.xchange.examples.itbit.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.itbit.v1.ItBitExchange;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * Tests all calls to itBit
 */
public class ItBitTradesDemo {

  public static void main(String[] args) throws Exception {

    // Use the factory to get BTC-E exchange API using default settings
    Exchange bitfinex = ExchangeFactory.INSTANCE.createExchange(ItBitExchange.class.getName());
    ExchangeSpecification defaultExchangeSpecification = bitfinex.getDefaultExchangeSpecification();

    defaultExchangeSpecification.setUserName("xxx");
    defaultExchangeSpecification.setApiKey("xxx");
    defaultExchangeSpecification.setSecretKey("xxx");

    defaultExchangeSpecification.setExchangeSpecificParametersItem("walletId", "xx");
    defaultExchangeSpecification.setExchangeSpecificParametersItem("userId", "xxx");

    bitfinex.applySpecification(defaultExchangeSpecification);

    // get all services
    PollingMarketDataService marketDataService = bitfinex.getPollingMarketDataService();
    PollingAccountService accout = bitfinex.getPollingAccountService();
    PollingTradeService trades = bitfinex.getPollingTradeService();

    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair("XBT", "USD"));
    System.out.println("BIDS: " + orderBook.getBids());
    System.out.println("ASKS: " + orderBook.getAsks());

    Trades trades2 = marketDataService.getTrades(new CurrencyPair("XBT", "USD"), 22233);
    System.out.println("Current trades:" + trades2);

    AccountInfo accountInfo = accout.getAccountInfo();

    System.out.println(accountInfo);
    OpenOrders openOrders = trades.getOpenOrders();
    System.out.println("open orders: " + openOrders);

    for (LimitOrder o : openOrders.getOpenOrders()) {
      System.out.println("Order " + o.getId() + " .. cancelling!");
      System.out.println(trades.cancelOrder(o.getId()));

    }

    String placeLimitOrder1 = trades.placeLimitOrder(new LimitOrder(OrderType.BID, BigDecimal.valueOf(10), new CurrencyPair("XBT", "USD"), "0", new Date(), BigDecimal.valueOf(300)));
    String placeLimitOrder2 = trades.placeLimitOrder(new LimitOrder(OrderType.BID, BigDecimal.valueOf(10), new CurrencyPair("XBT", "USD"), "0", new Date(), BigDecimal.valueOf(350)));
    String placeLimitOrder3 = trades.placeLimitOrder(new LimitOrder(OrderType.BID, BigDecimal.valueOf(10), new CurrencyPair("XBT", "USD"), "0", new Date(), BigDecimal.valueOf(360)));
    String placeLimitOrder4 = trades.placeLimitOrder(new LimitOrder(OrderType.BID, BigDecimal.valueOf(10), new CurrencyPair("XBT", "USD"), "0", new Date(), BigDecimal.valueOf(370)));

    System.out.println("limit order id " + placeLimitOrder1);
    System.out.println("limit order id " + placeLimitOrder2);
    System.out.println("limit order id " + placeLimitOrder3);
    System.out.println("limit order id " + placeLimitOrder4);

    trades.placeLimitOrder(new LimitOrder(OrderType.ASK, BigDecimal.valueOf(10), new CurrencyPair("XBT", "USD"), "0", new Date(), BigDecimal.valueOf(770)));

    System.out.println("Cancelling " + placeLimitOrder1);
    trades.cancelOrder(placeLimitOrder1);

    Trades tradeHistory = trades.getTradeHistory();
    System.out.println("Trade history: " + tradeHistory);
  }
}