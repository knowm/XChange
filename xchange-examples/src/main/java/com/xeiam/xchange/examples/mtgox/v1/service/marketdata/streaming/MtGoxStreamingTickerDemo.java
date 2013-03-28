/**
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.mtgox.MtGoxExchangeServiceConfiguration;
import com.xeiam.xchange.mtgox.MtGoxExchangeServiceConfiguration.Channel;
import com.xeiam.xchange.mtgox.v1.MtGoxExchange;
import com.xeiam.xchange.service.ExchangeEvent;
import com.xeiam.xchange.service.ExchangeEventType;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;

/**
 * Test requesting streaming Ticker at MtGox using 2 separate ticker streams BTC/USD and BTC/EUR
 */
public class MtGoxStreamingTickerDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    MtGoxStreamingTickerDemo tickerDemo = new MtGoxStreamingTickerDemo();
    tickerDemo.start();
  }

  public void start() throws ExecutionException, InterruptedException {

    // Use the default MtGox settings
    Exchange mtGoxExchange = ExchangeFactory.INSTANCE.createExchange(MtGoxExchange.class.getName());

    // Configure BTC/USD ticker stream for MtGox
    MtGoxExchangeServiceConfiguration btcusdConfiguration = new MtGoxExchangeServiceConfiguration(Currencies.BTC, Currencies.USD, Channel.ticker);

    // Interested in the public streaming market data feed (no authentication)
    StreamingMarketDataService btcusdStreamingMarketDataService = mtGoxExchange.getStreamingMarketDataService(btcusdConfiguration);

    // Open the connections to the exchange
    btcusdStreamingMarketDataService.connect();

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    Future<?> btcusd = executorService.submit(new TickerRunnable(btcusdStreamingMarketDataService));

    btcusd.get();

    executorService.shutdown();

    // TODO disconnect() currently shuts down all SocketIO traffic
    // Disconnect and exit
    System.out.println(Thread.currentThread().getName() + ": Disconnecting...");
    btcusdStreamingMarketDataService.disconnect();
  }

  /**
   * Encapsulates some ticker monitoring behavior
   */
  class TickerRunnable implements Runnable {

    private final StreamingMarketDataService marketDataService;

    /**
     * Constructor
     * 
     * @param marketDataService
     */
    public TickerRunnable(StreamingMarketDataService marketDataService) {

      this.marketDataService = marketDataService;
    }

    @Override
    public void run() {

      try {

        while (true) {

          ExchangeEvent exchangeEvent = marketDataService.getNextEvent();
          if (exchangeEvent.getEventType() == ExchangeEventType.TICKER) {
            Ticker ticker = (Ticker) exchangeEvent.getPayload();
            System.out.println(ticker.toString());
          }
        }

      } catch (InterruptedException e) {
        System.out.println("ERROR in Runnable!!!");
      }

    }
  }
}
