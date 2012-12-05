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

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.mtgox.v1.MtGoxExchange;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;

/**
 * Test requesting streaming Ticker at MtGox
 */
public class TickerDemo {

  public static void main(String[] args) {

    TickerDemo tickerDemo = new TickerDemo();
    tickerDemo.start();
  }

  private void start() {

    // Use the default MtGox settings
    Exchange mtGox = MtGoxExchange.newInstance();

    // Interested in the public streaming market data feed (no authentication)
    StreamingMarketDataService streamingMarketDataService = mtGox.getStreamingMarketDataService();

    // Get blocking queue that receives streaming ticker data
    BlockingQueue<Ticker> tickerQueue = streamingMarketDataService.requestTicker(Currencies.BTC, Currencies.USD);

    // note: probably won't be connected quite yet
    // System.out.println("isConnected: " + streamingMarketDataService.isConnected());

    // take streaming ticker data from the queue and do something with it
    while (true) {
      try {
        // Put your ticker event handling code here
        doSomething(tickerQueue.take());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

  /**
   * Do something fun with the streaming data!
   * 
   * @param ticker
   */
  private void doSomething(Ticker ticker) {

    // System.out.println(ticker.toString());
  }

}
