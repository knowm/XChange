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
package com.xeiam.xchange.mtgox.v1.service.marketdata.streaming.socketio;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.service.BaseSocketIOExchangeService;
import com.xeiam.xchange.service.ExchangeEvent;
import com.xeiam.xchange.service.ExchangeEventType;
import com.xeiam.xchange.service.RunnableExchangeEventListener;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;
import com.xeiam.xchange.utils.Assert;

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
  private final String apiBase = String.format("http://socketio.%s/mtgox", exchangeSpecification.getHost());

  private final RunnableExchangeEventListener runnableExchangeEventListener;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The exchange specification providing the required connection data
   */
  public MtGoxStreamingMarketDataService(ExchangeSpecification exchangeSpecification) throws IOException {

    super(exchangeSpecification);

    // Create the listener for the specified eventType
    this.runnableExchangeEventListener = new MtGoxRunnableExchangeEventListener(consumerEventQueue);

  }

  /**
   * Initiates a connection to Mt Gox
   * 
   * @param tradableIdentifier An exchange-specific identifier (e.g. "BTC" but can be null)
   * @param currency An exchange-specific currency identifier (e.g. "USD" but can be null)
   * @return The blocking queue of exchange events
   */
  @Override
  public BlockingQueue<ExchangeEvent> getEventQueue(String tradableIdentifier, final String currency, ExchangeEventType event) {

    log.info("Verifying...");

    verify(tradableIdentifier, currency);

    connectStream(currency, runnableExchangeEventListener, event);

    return consumerEventQueue;
  }

  private void connectStream(String currency, RunnableExchangeEventListener listener, ExchangeEventType event) {

    String url = "";
    log.debug("Connecting to channel = " + event.toString());

    if (event.equals(ExchangeEventType.TICKER)) {
      url = apiBase + "?Channel=ticker&Currency=" + currency;
      log.debug("streaming url= " + url);
    }
    if (event.equals(ExchangeEventType.TRADE)) {
      url = apiBase + "?Channel=trades&Currency=" + currency;
      log.debug("streaming url= " + url);
    }
    if (event.equals(ExchangeEventType.DEPTH)) {
      url = apiBase + "?Channel=depth&Currency=" + currency;
      log.debug("streaming url= " + url);
    }

    connect(url, listener);
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
   * Verify that the exchange can provide a stream
   * 
   * @param tradableIdentifier The tradable identifier (exchange specific)
   * @param currency The currency
   */
  private void verify(String tradableIdentifier, String currency) {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(MtGoxUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

}
