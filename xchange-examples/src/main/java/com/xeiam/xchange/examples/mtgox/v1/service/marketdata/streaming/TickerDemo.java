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
package com.xeiam.xchange.examples.mtgox.v1.service.marketdata.streaming;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.ExchangeEvent;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;

/**
 * Test requesting streaming Ticker at MtGox
 */
public class TickerDemo {

  private static final Logger log = LoggerFactory.getLogger(TickerDemo.class);

  public static void main(String[] args) {

    TickerDemo tickerDemo = new TickerDemo();
    tickerDemo.start();
  }

  private void start() {

    // Use the default MtGox settings
    Exchange mtGox = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.mtgox.v1.MtGoxExchange");

    // Interested in the public streaming market data feed (no authentication)
    StreamingMarketDataService streamingMarketDataService = mtGox.getStreamingMarketDataService();

    // Get blocking queue that receives streaming ticker data
    BlockingQueue<Ticker> tickerQueue = streamingMarketDataService.getTickerQueue(Currencies.BTC, Currencies.USD);

    // Get blocking queue that receives exchange event data
    BlockingQueue<ExchangeEvent> eventQueue = streamingMarketDataService.getEventQueue();

    // Take streaming ticker data from the queue and do something with it for the first few ticks
    int count = 0;
    try {
      while (true) {
        // Exhaust exchange events first
        while (!eventQueue.isEmpty()) {
          ExchangeEvent exchangeEvent = eventQueue.take();
          log.info("Exchange event: {} {}", exchangeEvent.getEventType().name(), new String(exchangeEvent.getRawData()));
        }

        // Check for Tickers
        if (!tickerQueue.isEmpty()) {
          doSomething(tickerQueue.take());
          count++;
        }
      }

      // log.info("Disconnecting (event queue threads will be suspended)...");
      // streamingMarketDataService.disconnect();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Do something fun with the streaming data!
   * 
   * @param ticker The market data ticker
   */
  private void doSomething(Ticker ticker) {

    log.info(ticker.toString());
  }

}
