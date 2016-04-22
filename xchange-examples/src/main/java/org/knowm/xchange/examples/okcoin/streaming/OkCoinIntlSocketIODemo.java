package org.knowm.xchange.examples.okcoin.streaming;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.okcoin.OkCoinExchange;
import org.knowm.xchange.okcoin.service.streaming.OkCoinExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

public class OkCoinIntlSocketIODemo {

  public static void main(String[] args) throws Exception {

    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    exSpec.setSecretKey("aa");
    exSpec.setApiKey("bb");

    //Force intl
    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    //Forcing the currency pair is required for Intl
    final StreamingExchangeService service = exchange
        .getStreamingExchangeService(new OkCoinExchangeStreamingConfiguration(new CurrencyPair[] { CurrencyPair.BTC_USD }));

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
