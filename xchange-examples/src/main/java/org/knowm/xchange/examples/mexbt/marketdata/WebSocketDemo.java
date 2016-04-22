package org.knowm.xchange.examples.mexbt.marketdata;

import java.util.concurrent.TimeUnit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.mexbt.MeXBTExchange;
import org.knowm.xchange.mexbt.service.streaming.MeXBTExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

public class WebSocketDemo {

  public static void main(String[] args) throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(MeXBTExchange.class.getName());
    final StreamingExchangeService streamingExchangeService = exchange
        .getStreamingExchangeService(new MeXBTExchangeStreamingConfiguration(true, new String[] { "BTCUSD" }));
    streamingExchangeService.connect();

    new Thread() {

      @Override
      public void run() {
        while (true) {
          ExchangeEvent exchangeEvent;
          try {
            exchangeEvent = streamingExchangeService.getNextEvent();
            System.out.println(exchangeEvent.getEventType() + ": " + exchangeEvent.getPayload());
          } catch (InterruptedException e) {
            System.err.println(e.getMessage());
          }
        }
      }

    }.start();

    TimeUnit.MINUTES.sleep(5);
  }

}
