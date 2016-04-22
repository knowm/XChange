package org.knowm.xchange.examples.huobi.marketdata.streaming;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.huobi.HuobiExchange;
import org.knowm.xchange.huobi.service.streaming.HuobiExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

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
