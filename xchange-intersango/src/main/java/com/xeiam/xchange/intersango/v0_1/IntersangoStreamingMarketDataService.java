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
package com.xeiam.xchange.intersango.v0_1;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.marketdata.streaming.MarketDataEvent;
import com.xeiam.xchange.service.marketdata.streaming.MarketDataListener;
import com.xeiam.xchange.service.marketdata.streaming.SocketStreamingMarketDataService;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;

/**
 * <p>Streaming market data service for the Intersango exchange</p>
 * <p>Intersango provide a direct socket implementation so no SocketIO or websockets</p>
 */
public class IntersangoStreamingMarketDataService implements StreamingMarketDataService {

  SocketStreamingMarketDataService socketStreamingMarketDataService;
  
  public IntersangoStreamingMarketDataService(ExchangeSpecification exchangeSpecification) {
    String host = exchangeSpecification.getHost();
    int port = exchangeSpecification.getPort();
    
    socketStreamingMarketDataService = new SocketStreamingMarketDataService(host,port);
  }

  @Override
  public void addListener(MarketDataListener marketDataListener) {
    // TODO Implement this
  }

  @Override
  public void removeListener(MarketDataListener marketDataListener) {
    // TODO Implement this
  }

  @Override
  public void fireMarketDataEvent(MarketDataEvent marketDataEvent) {
    // TODO Implement this
  }

  @Override
  public void connect() {
    // TODO Implement this
  }

  @Override
  public void disconnect() {
    // TODO Implement this
  }
}
