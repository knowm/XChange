/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.examples.bitstamp.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitstamp.BitstampExchange;
import com.xeiam.xchange.bitstamp.service.streaming.BitstampStreamingConfiguration;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * Demonstrate requesting Depth at Bitstamp
 */
public class StreamingDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Bitstamp exchange API using default settings
    Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
    BitstampStreamingConfiguration streamCfg = new BitstampStreamingConfiguration();
    // Interested in the public streaming market data feed (no authentication)
    StreamingExchangeService streamService = bitstamp.getStreamingExchangeService(streamCfg);
    streamService.connect();
    generic(streamService);
    streamService.disconnect();
  }

  private static void generic(StreamingExchangeService stream) throws IOException {

    try {
      for (int i = 0; i < 10; i++) {
        ExchangeEvent evt = stream.getNextEvent();
        switch (evt.getEventType()) {
        case SUBSCRIBE_ORDERS:
          printOrderBook((OrderBook) evt.getPayload());
          break;
        }

      }
      System.out.println("Closing Bitstamp stream.");
    } catch (InterruptedException e) {
      e.printStackTrace(System.err);
    }
  }

  private static void printOrderBook(OrderBook orderBook) {

    System.out.println(orderBook.toString());
  }

}
