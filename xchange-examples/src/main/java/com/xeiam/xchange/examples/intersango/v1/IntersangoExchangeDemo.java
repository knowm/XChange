package com.xeiam.xchange.examples.intersango.v1;

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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.joda.money.BigMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.AccountInfo;
import com.xeiam.xchange.intersango.v0_1.IntersangoExchange;
import com.xeiam.xchange.service.marketdata.async.AsyncMarketDataService;
import com.xeiam.xchange.service.marketdata.streaming.MarketDataEvent;
import com.xeiam.xchange.service.marketdata.streaming.RunnableMarketDataListener;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;
import com.xeiam.xchange.service.trade.async.AsyncTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connecting to Intersango BTC exchange</li>
 * <li>Retrieving market data</li>
 * <li>Retrieving basic trade data</li>
 * <li>Retrieving authenticated account data</li>
 * </ul>
 */
public class IntersangoExchangeDemo {

  private static final Logger log = LoggerFactory.getLogger(IntersangoExchangeDemo.class);

  /**
   * @param args [0] is the API key provided by Intersango
   */
  public static void main(String[] args) {

    Exchange exchange = IntersangoExchange.newInstance();

    // Demonstrate the public market data service
    demoMarketDataService(exchange);

    // Demonstrate the streaming market data service
    demoStreamingMarketDataService(exchange);

    // Demonstrate the private account data service
    demoAccountService(exchange);

  }

  /**
   * Demonstrates how to connect to the MarketDataService for Intersango
   * 
   * @param exchange The exchange
   */
  private static void demoMarketDataService(Exchange exchange) {

    // Interested in the public market data feed (no authentication)
    AsyncMarketDataService marketDataService = exchange.getAsyncMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(Currencies.BTC, Currencies.USD);

    BigMoney btcusd = ticker.getLast();

    log.debug("Current exchange rate: {}", btcusd.multipliedBy(10000).toString());
  }

  /**
   * Demonstrates how to connect to the AccountService for Intersango
   * 
   * @param exchange The exchange
   */
  private static void demoAccountService(Exchange exchange) {

    // Interested in the private data feed (requires authentication)
    AsyncTradeService accountService = exchange.getTradeService();

    // Get the latest ticker data showing BTC to USD
    AccountInfo accountInfo = accountService.getAccountInfo();

    log.debug("Account info: {}", accountInfo);
  }

  /**
   * Demonstrates how to connect to the AccountService for Intersango
   * 
   * @param exchange The exchange
   */
  private static void demoStreamingMarketDataService(Exchange exchange) {

    // Construct an Exchange that we know to use a direct socket to support streaming market data
    Exchange intersango = IntersangoExchange.newInstance();
    StreamingMarketDataService streamingMarketDataService = intersango.getStreamingMarketDataService();

    // Create a runnable listener so we can bind it to a thread
    RunnableMarketDataListener listener = new RunnableMarketDataListener() {
      @Override
      public void handleEvent(MarketDataEvent event) {
        // Perform very basic reporting to illustrate different threads
        String data = new String(event.getRawData());
        log.debug("Event data: {}", data);
      }

    };
    streamingMarketDataService.start(listener);

    // Start a new thread for the listener
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(listener);

  }

}
