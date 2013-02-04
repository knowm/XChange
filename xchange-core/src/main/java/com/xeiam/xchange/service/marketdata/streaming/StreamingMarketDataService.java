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
package com.xeiam.xchange.service.marketdata.streaming;

import java.util.concurrent.BlockingQueue;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.service.ExchangeEvent;
import com.xeiam.xchange.service.ExchangeEventType;
import com.xeiam.xchange.service.StreamingExchangeService;

/**
 * <p>
 * Interface to provide the following to {@link Exchange}:
 * </p>
 * <ul>
 * <li>Standard methods available to explore the market data</li>
 * </ul>
 * <p>
 * The implementation of this service is expected to be based on a client streaming mechanism of some kind
 * </p>
 */
public interface StreamingMarketDataService extends StreamingExchangeService {

  /**
   * Request a streaming feed
   *
   * @param tradableIdentifier The tradeable identifier (e.g. "Bitcoin")
   * @param currency The currency symbol
   * @return A blocking queue consisting of received event objects
   * @event Streaming data connection to listen, specified by event type
   */
//  @Deprecated
//  BlockingQueue<Ticker> getTickerQueue(String tradableIdentifier, String currency);
//  BlockingQueue<Trade> getTradeQueue(String tradableIdentifier, String currency);
//  BlockingQueue<Trade> getDepthQueue(String tradableIdentifier, String currency);
  BlockingQueue<ExchangeEvent> getEventQueue(String tradableIdentifier, final String currency, ExchangeEventType event);

  /**
   * Cancel the streaming Ticker feed
   */
  void cancelTicker();

}
