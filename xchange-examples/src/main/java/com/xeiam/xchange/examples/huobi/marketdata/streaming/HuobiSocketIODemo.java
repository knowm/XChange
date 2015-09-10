package com.xeiam.xchange.examples.huobi.marketdata.streaming;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.huobi.HuobiExchange;
import com.xeiam.xchange.huobi.service.streaming.HuobiExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventType;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

public class HuobiSocketIODemo {

  public static void main(String[] args) throws Exception {

    ExchangeSpecification exSpec = new ExchangeSpecification(HuobiExchange.class);
    exSpec.setSecretKey("aa");
    exSpec.setApiKey("bb");

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    final StreamingExchangeService service = exchange.getStreamingExchangeService(new HuobiExchangeStreamingConfiguration());

    Thread consumer = new Thread("consumer") {

      @Override
      public void run() {

        while (!isInterrupted()) {
          try {
            ExchangeEvent event = service.getNextEvent();

            if (event != null) {
              System.out.println("---> " + event.getPayload() + " " + event.getEventType());

              if (event.getEventType().equals(ExchangeEventType.TICKER)) {
                Ticker ticker = (Ticker) event.getPayload();

                long x = (new Date()).getTime() - ticker.getTimestamp().getTime();
                System.out.println("Delay " + x);
              }

            }

          } catch (InterruptedException e) {
            this.interrupt();
          }
        }
      }

    };

    // Start consumer.
    consumer.start();

    // Start streaming service.
    service.connect();

    // Demonstrate for 30 seconds.
    TimeUnit.SECONDS.sleep(30);

    // Disconnect streaming service.
    service.disconnect();

    // Interrupt consumer to exit.
    consumer.interrupt();
  }

}
