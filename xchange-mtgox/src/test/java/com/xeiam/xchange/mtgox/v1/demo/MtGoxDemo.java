/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v1.demo;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.mtgox.v1.MtGoxProperties;
import com.xeiam.xchange.service.marketdata.MarketDataService;
import com.xeiam.xchange.service.marketdata.OrderBook;
import com.xeiam.xchange.service.marketdata.Ticker;
import com.xeiam.xchange.service.trade.AccountInfo;
import com.xeiam.xchange.service.trade.TradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connecting to Mt Gox Bitcoin exchange</li>
 * <li>Retrieving market data</li>
 * </ul>
 * 
 * @since 0.0.1
 */
public class MtGoxDemo {

  public static void main(String[] args) {

    // Demonstrate the public market data service
    demoMarketDataService();

    // Demonstrate the private account data service
    // demoAccountService();
  }

  /**
   * Demonstrates how to connect to the MarketDataService for MtGox
   */
  private static void demoMarketDataService() {

    // Use the factory to get the version 1 MtGox exchange API using default settings
    Exchange mtGox = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.mtgox.v1.MtGoxExchange");

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = mtGox.getMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker("BTCUSD");
    double btcusd = (double) ticker.getLast() / MtGoxProperties.PRICE_INT_2_DECIMAL_FACTOR;
    System.out.println("Current exchange rate for BTC to USD: " + btcusd);

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getOrderBook("BTCUSD");
    System.out.println(orderBook.getAsks().get(0).getStamp());
    System.out.println("orderBook as String: " + orderBook.toString());

    // Get the current full orderbook
    OrderBook fullOrderBook = marketDataService.getFullOrderBook("BTCUSD");
    System.out.printf("full depth as String: ", fullOrderBook.toString());

  }

  /**
   * Demonstrates how to connect to the AccountService for MtGox
   */
  private static void demoAccountService() {

    // Use the factory to get the version 1 MtGox exchange API using default settings
    Exchange mtgox = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.mtgox.v1.MtGoxExchange");

    // Interested in the public market data feed (no authentication)
    TradeService accountService = mtgox.getAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getExchangeAccountInfo("sessionKey", "secretKey");

    System.out.printf("Account info: %s", accountInfo);
  }

}
