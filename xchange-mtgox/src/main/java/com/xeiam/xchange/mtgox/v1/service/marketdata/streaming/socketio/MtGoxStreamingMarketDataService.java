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
package com.xeiam.xchange.mtgox.v1.service.marketdata.streaming.socketio;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.mtgox.v1.MtGoxExchange;
import com.xeiam.xchange.service.BaseSocketIOExchangeService;
import com.xeiam.xchange.service.ExchangeEvent;
import com.xeiam.xchange.service.RunnableExchangeEventListener;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;

/**
 * <p>
 * Streaming market data service for the MtGox exchange
 * </p>
 * <p>
 * MtGox provides a SocketIO implementation
 * </p>
 */
public class MtGoxStreamingMarketDataService extends BaseSocketIOExchangeService implements StreamingMarketDataService {

  private final Logger log = LoggerFactory.getLogger(MtGoxStreamingMarketDataService.class);

  /**
   * Configured from the super class reading of the exchange specification
   */
  // private final String apiBase = String.format("http://socketio.%s", exchangeSpecification.getHost());

  private final String apiBase = String.format("http://socketio.%s/mtgox", exchangeSpecification.getHost());

  /**
   * Constructor
   * 
   * @param exchangeSpecification The exchange specification providing the required connection data
   */
  public MtGoxStreamingMarketDataService(ExchangeSpecification exchangeSpecification) throws IOException {
    super(exchangeSpecification);
  }

  @Override
  public void getTicker() throws NotAvailableFromExchangeException {

    // log.debug(apiBase);

    // Construct an Exchange that we know to use a direct socket to support streaming market data
    Exchange mtGox = MtGoxExchange.newInstance();
    StreamingMarketDataService streamingExchangeService = mtGox.getStreamingMarketDataService();

    // Create a runnable listener so we can bind it to a thread
    RunnableExchangeEventListener listener = new RunnableExchangeEventListener() {
      @Override
      public void handleEvent(ExchangeEvent event) {
        // Perform very basic reporting to illustrate different threads
        String data = new String(event.getRawData());
        log.debug("Event data: {}", data);
      }
    };

    streamingExchangeService.connect(apiBase, listener);
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    streamingExchangeService.send("{\"op\": \"unsubscribe\",\"channel\": \"d5f06780-30a8-4a48-a2f8-7ed181b4a13f\"}"); // ticker
    streamingExchangeService.send("{\"op\": \"unsubscribe\",\"channel\": \"dbf1dee9-4f2e-4a08-8cb7-748919a71b21\"}"); // trades
    streamingExchangeService.send("{\"op\": \"unsubscribe\",\"channel\": \"24e67e0d-1cad-4cc0-9e7a-f8523ef460fe\"}"); // depth
    streamingExchangeService.send("{\"op\":\"mtgox.subscribe\",\"type\":\"ticker\"}");

    // Start a new thread for the listener
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(listener);

  }

}
