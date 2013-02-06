/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.examples.mtgox.v1.service.marketdata.streaming;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.mtgox.MtGoxExchangeServiceConfiguration;
import com.xeiam.xchange.mtgox.v1.MtGoxExchange;
import com.xeiam.xchange.service.ExchangeEvent;
import com.xeiam.xchange.service.ExchangeEventType;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Test requesting streaming Ticker at MtGox using 2 separate ticker streams BTC/USD and BTC/EUR
 */
public class MtGoxStreamingTickerDemo {

  public static void main(String[] args) {

    MtGoxStreamingTickerDemo tickerDemo = new MtGoxStreamingTickerDemo();
    tickerDemo.start();
  }

  public void start() {

    // Use the default MtGox settings
    Exchange mtGoxExchange = ExchangeFactory.INSTANCE.createExchange(MtGoxExchange.class.getName());

    // Configure BTC/USD ticker stream for MtGox
    MtGoxExchangeServiceConfiguration btcusdConfiguration = new MtGoxExchangeServiceConfiguration(
      Currencies.BTC,
      Currencies.USD
    );

    // Configure BTC/EUR ticker stream for MtGox
    MtGoxExchangeServiceConfiguration btceurConfiguration = new MtGoxExchangeServiceConfiguration(
      Currencies.BTC,
      Currencies.EUR
    );

    // Interested in the public streaming market data feed (no authentication)
    StreamingMarketDataService btcusdStreamingMarketDataService = mtGoxExchange.getStreamingMarketDataService(btcusdConfiguration);
    StreamingMarketDataService btceurStreamingMarketDataService = mtGoxExchange.getStreamingMarketDataService(btceurConfiguration);

    // Open the connections to the exchange
    btcusdStreamingMarketDataService.connect();
    btceurStreamingMarketDataService.connect();

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.submit(new TickerRunnable(btcusdStreamingMarketDataService));
    executorService.submit(new TickerRunnable(btceurStreamingMarketDataService));

  }

}

/**
 * Encapsulates some ticker monitoring behaviour
 */
class TickerRunnable implements Runnable {

  private final StreamingMarketDataService marketDataService;

  TickerRunnable(StreamingMarketDataService marketDataService) {
    this.marketDataService = marketDataService;
  }

  @Override
  public void run() {

    BlockingQueue<ExchangeEvent> eventQueue = marketDataService.getEventQueue();

    try {

      // Run for a limited number of events
      for (int i = 0; i < 5; i++) {

        // Monitor the exchange events
        System.out.println("Waiting for exchange event...");
        ExchangeEvent exchangeEvent = eventQueue.take();
        System.out.println("Exchange event: " + exchangeEvent.getEventType().name() + ", " + exchangeEvent.getData());

        if (exchangeEvent.getEventType() == ExchangeEventType.TICKER) {

          Ticker ticker = (Ticker) exchangeEvent.getPayload();
          System.out.println("+ Ticker: " + ticker.toString());

        }

      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      // Disconnect and exit
      marketDataService.disconnect();


    }

  }
}