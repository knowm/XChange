package com.xeiam.xchange.examples.btcchina.streaming;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.service.streaming.BTCChinaStreamingConfiguration;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.examples.btcchina.BTCChinaExamplesUtils;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

public class BTCChinaSocketIOServiceDemo {

  private static final Logger log = LoggerFactory.getLogger(BTCChinaSocketIOServiceDemo.class);

  public static void main(String[] args) throws InterruptedException {

    final BTCChinaStreamingConfiguration configuration = new BTCChinaStreamingConfiguration(true, true, true, true, CurrencyPair.BTC_CNY,
        CurrencyPair.LTC_CNY, CurrencyPair.LTC_BTC);
    final Exchange exchange = BTCChinaExamplesUtils.getExchange();
    final StreamingExchangeService service = exchange.getStreamingExchangeService(configuration);

    Thread consumer = new Thread("consumer") {

      @Override
      public void run() {

        while (!isInterrupted()) {
          try {
            ExchangeEvent event = service.getNextEvent();
            log.info("status: {}, type: {}, data: {}, payload: {}", service.getWebSocketStatus(), event.getEventType(), event.getData(),
                event.getPayload());
          } catch (InterruptedException e) {
            this.interrupt();
          }
        }
      }

    };

    // Start consumer.
    consumer.start();
    log.info("Consumer started.");

    // Start streaming service.
    service.connect();
    log.info("Streaming service started.");

    // Demonstrate for 30 seconds.
    TimeUnit.SECONDS.sleep(30);

    // Disconnect streaming service.
    service.disconnect();
    log.info("Streaming service disconnected.");

    // Interrupt consumer to exit.
    consumer.interrupt();
    log.info("Consumer interrupted.");
  }

}
