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
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.mtgox.v1.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v1.MtGoxUtils;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.service.BaseSocketIOExchangeService;
import com.xeiam.xchange.service.ExchangeEvent;
import com.xeiam.xchange.service.RunnableExchangeEventListener;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;
import com.xeiam.xchange.utils.Assert;
import com.xeiam.xchange.utils.JSONUtils;

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

  ObjectMapper tickerObjectMapper = new ObjectMapper();

  /**
   * Configured from the super class reading of the exchange specification
   */
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
  public BlockingQueue<Ticker> requestTicker(String tradableIdentifier, String currency) {

    log.info("requesting Ticker...");

    verify(tradableIdentifier, currency);

    // blocking ticker queue
    final BlockingQueue<Ticker> tickerQueue = new LinkedBlockingQueue<Ticker>(1024);

    // Create a runnable listener so we can bind it to a thread
    RunnableExchangeEventListener listener = new RunnableExchangeEventListener() {

      @Override
      public void handleEvent(ExchangeEvent exchangeEvent) {

        String eventContent = new String(exchangeEvent.getRawData());
        // log.debug("Event data: {}", eventContent);

        // get raw JSON
        Map<String, Object> rawJSON = JSONUtils.getJsonGenericMap(eventContent, tickerObjectMapper);

        if (rawJSON.get("ticker") == null) { // some JSON came in that is not mtgox ticker data
          log.info(eventContent);
          log.info("socketIO.isConnected(): " + socketIO.isConnected());

          return;
        }

        // Get MtGoxTicker from JSON String
        MtGoxTicker mtGoxTicker = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("ticker"), tickerObjectMapper), MtGoxTicker.class, tickerObjectMapper);

        // Adapt to XChange DTOs
        Ticker ticker = MtGoxAdapters.adaptTicker(mtGoxTicker);
        // log.debug(ticker.toString());

        try {
          tickerQueue.put(ticker);
        } catch (InterruptedException e) {
          throw new ExchangeException("InterruptedException!", e);
        }
      }
    };

    String url = apiBase + "?Channel=ticker&Currency=" + currency;
    log.debug(url);
    this.connect(url, listener);

    // Start a new thread for the listener
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(listener);

    return tickerQueue;

  }

  @Override
  public void cancelTicker() {

    disconnect();
  }

  @Override
  public boolean isConnected() {

    return socketIO.isConnected();
  }

  /**
   * Verify
   * 
   * @param tradableIdentifier
   * @param currency
   */
  private void verify(String tradableIdentifier, String currency) {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(MtGoxUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

}
