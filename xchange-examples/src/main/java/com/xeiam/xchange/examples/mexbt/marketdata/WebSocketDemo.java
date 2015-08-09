package com.xeiam.xchange.examples.mexbt.marketdata;

import java.util.concurrent.TimeUnit;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.mexbt.MeXBTExchange;
import com.xeiam.xchange.mexbt.service.streaming.MeXBTExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

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
