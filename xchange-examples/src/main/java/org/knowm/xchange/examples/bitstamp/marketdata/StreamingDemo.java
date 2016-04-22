package org.knowm.xchange.examples.bitstamp.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.bitstamp.service.streaming.BitstampStreamingConfiguration;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

/**
 * Demonstrate requesting Depth at Bitstamp
 */
public class StreamingDemo {

  public static void main(String[] args) throws Exception {

    // Use the factory to get Bitstamp exchange API using default settings
    Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
    BitstampStreamingConfiguration streamCfg = new BitstampStreamingConfiguration();
    // Interested in the public streaming market data feed (no authentication)
    StreamingExchangeService streamService = bitstamp.getStreamingExchangeService(streamCfg);
    streamService.connect();
    go(streamService);
    streamService.disconnect();
  }

  private static void go(StreamingExchangeService streamService) throws IOException {

    try {
      for (int i = 0; i < 10; i++) {
        ExchangeEvent evt = streamService.getNextEvent();
        switch (evt.getEventType()) {
        case SUBSCRIBE_ORDERS:
          System.out.println(((OrderBook) evt.getPayload()).toString());
          break;
        case DEPTH:
          System.out.println(((OrderBook) evt.getPayload()).toString());
          break;
        case TRADE:
          System.out.println(((Trade) evt.getPayload()).toString());
          break;
        default:
          break;
        }

      }
      System.out.println("Closing Bitstamp stream.");
    } catch (InterruptedException e) {
      e.printStackTrace(System.err);
    }
  }

}
