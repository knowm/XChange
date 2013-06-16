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
package com.xeiam.xchange.examples.mtgox.v2.service.marketdata.streaming;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.OrderBookUpdate;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.mtgox.v2.MtGoxExchange;
import com.xeiam.xchange.mtgox.v2.service.streaming.MtGoxStreamingConfiguration;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventType;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * Demonstrate streaming market data from the MtGox Websocket API and dynamically updating the OrderBook
 */
public class MtGoxWebSocketSyncronizedOrderBookDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    MtGoxWebSocketSyncronizedOrderBookDemo streamingTickerDemo = new MtGoxWebSocketSyncronizedOrderBookDemo();
    streamingTickerDemo.start();
  }

  public void start() throws ExecutionException, InterruptedException {

    // Use the default MtGox settings
    Exchange mtGoxExchange = ExchangeFactory.INSTANCE.createExchange(MtGoxExchange.class.getName());

    // Configure BTC/USD ticker stream for MtGox
    ExchangeStreamingConfiguration btcusdConfiguration = new MtGoxStreamingConfiguration(10, 10000, 60000, false, "depth.BTCUSD");

    // Interested in the public streaming market data feed (no authentication)
    StreamingExchangeService btcusdStreamingMarketDataService = mtGoxExchange.getStreamingExchangeService(btcusdConfiguration);

    // Requesting initial order book using the polling service
    PollingMarketDataService marketDataService = mtGoxExchange.getPollingMarketDataService();
    MarketDataRunnable.orderBook = marketDataService.getPartialOrderBook(Currencies.BTC, Currencies.USD);

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
  public static class MarketDataRunnable implements Runnable {

    private final StreamingExchangeService streamingExchangeService;
    public static OrderBook orderBook;
    public static Ticker lastTicker;

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
            MarketDataRunnable.lastTicker = (Ticker) exchangeEvent.getPayload();
          }

          else if (exchangeEvent.getEventType() == ExchangeEventType.TRADE) {
            Trade trade = (Trade) exchangeEvent.getPayload();
            System.out.println(trade.toString());
          }

          else if (exchangeEvent.getEventType() == ExchangeEventType.DEPTH) {
            OrderBookUpdate update = (OrderBookUpdate) exchangeEvent.getPayload();
            if (update.getLimitOrder().getTransactionCurrency().equals("USD") && MarketDataRunnable.lastTicker != null) {
              MarketDataRunnable.orderBook.update(update);
              if (MarketDataRunnable.orderBook.getAsks().get(0).getLimitPrice().compareTo(MarketDataRunnable.lastTicker.getAsk()) != 0) {
                System.out.println("ERROR IN ORDERBOOK (ASKS) -> BOOK PRICE:" + MarketDataRunnable.orderBook.getAsks().get(0).getLimitPrice().getAmount().doubleValue() + " TICKER PRICE:"
                    + MarketDataRunnable.lastTicker.getAsk().getAmount().doubleValue());
              }
              else {
                System.out.println("ASKS BOOK OK!! " + MarketDataRunnable.lastTicker.getAsk().getAmount().doubleValue());
              }

              if (MarketDataRunnable.orderBook.getBids().get(0).getLimitPrice().compareTo(MarketDataRunnable.lastTicker.getBid()) != 0) {
                System.out.println("ERROR IN ORDERBOOK (BIDS) -> -> BOOK_PRICE:" + MarketDataRunnable.orderBook.getBids().get(0).getLimitPrice().getAmount().doubleValue() + " TICKER_PRICE:"
                    + MarketDataRunnable.lastTicker.getBid().getAmount().doubleValue());
              }
              else {
                System.out.println("BIDS BOOK OK!! " + MarketDataRunnable.lastTicker.getBid().getAmount().doubleValue());
              }
            }
          }
        }

      } catch (InterruptedException e) {
        System.out.println("ERROR in Runnable!!!");
      }

    }
  }
}
