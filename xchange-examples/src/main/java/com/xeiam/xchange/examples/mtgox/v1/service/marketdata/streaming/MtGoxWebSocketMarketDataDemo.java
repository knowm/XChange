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
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.mtgox.v1.MtGoxExchange;
import com.xeiam.xchange.mtgox.v1.service.marketdata.streaming.MtGoxStreamingConfiguration;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventType;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * Demonstrate streaming market data from the MtGox Websocket API
 * Note: requesting certain "channels" or specific currencies does not work. I believe this is the fault of MtGox and not XChange
 * 
 * @deprecated Use V2! This will be removed in 1.8.0+
 */
@Deprecated
public class MtGoxWebSocketMarketDataDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    MtGoxWebSocketMarketDataDemo streamingTickerDemo = new MtGoxWebSocketMarketDataDemo();
    streamingTickerDemo.start();
  }

  public void start() throws ExecutionException, InterruptedException {

    // Use the default MtGox settings
    Exchange mtGoxExchange = ExchangeFactory.INSTANCE.createExchange(MtGoxExchange.class.getName());

    // Configure BTC/USD ticker stream for MtGox
    ExchangeStreamingConfiguration btcusdConfiguration = new MtGoxStreamingConfiguration(10, 10000, Currencies.BTC, Currencies.EUR, false);

    // Interested in the public streaming market data feed (no authentication)
    StreamingExchangeService btcusdStreamingMarketDataService = mtGoxExchange.getStreamingExchangeService(btcusdConfiguration);

    // Open the connections to the exchange
    btcusdStreamingMarketDataService.connect();

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<?> mtGoxMarketDataFuture = executorService.submit(new MarketDataRunnable(btcusdStreamingMarketDataService));

    // the thread waits here until the Runnable is done.
    mtGoxMarketDataFuture.get();

    executorService.shutdown();

    // Disconnect and exit
    System.out.println(Thread.currentThread().getName() + ": Disconnecting...");
    btcusdStreamingMarketDataService.disconnect();
  }

  /**
   * Encapsulates some market data monitoring behavior
   */
  class MarketDataRunnable implements Runnable {

    private final StreamingExchangeService streamingExchangeService;

    /**
     * Constructor
     * 
     * @param streamingExchangeService
     */
    public MarketDataRunnable(StreamingExchangeService streamingExchangeService) {

      this.streamingExchangeService = streamingExchangeService;
    }

    @Override
    public void run() {

      try {

        while (true) {

          ExchangeEvent exchangeEvent = streamingExchangeService.getNextEvent();

          if (exchangeEvent.getEventType() == ExchangeEventType.TICKER) {
            // Ticker ticker = (Ticker) exchangeEvent.getPayload();
            // System.out.println(ticker.toString());
          }

          else if (exchangeEvent.getEventType() == ExchangeEventType.TRADE) {
            Trade trade = (Trade) exchangeEvent.getPayload();
            System.out.println(trade.toString());
          }

          else if (exchangeEvent.getEventType() == ExchangeEventType.DEPTH) {
            // OrderBookUpdate orderBookUpdate = (OrderBookUpdate) exchangeEvent.getPayload();
            // System.out.println(orderBookUpdate.toString());
          }
        }

      } catch (InterruptedException e) {
        System.out.println("ERROR in Runnable!!!");
      }

    }
  }
}
