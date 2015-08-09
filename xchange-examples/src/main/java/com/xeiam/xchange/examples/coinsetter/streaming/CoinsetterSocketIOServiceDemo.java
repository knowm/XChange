package com.xeiam.xchange.examples.coinsetter.streaming;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.coinsetter.CoinsetterExchange;
import com.xeiam.xchange.coinsetter.service.streaming.CoinsetterStreamingConfiguration;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

public class CoinsetterSocketIOServiceDemo {

  private static final Logger log = LoggerFactory.getLogger(CoinsetterSocketIOServiceDemo.class);

  public static void main(String[] args) throws Exception {

    // Bridge/route all JUL log records to the SLF4J API.
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    String customerUuid = args.length > 0 ? args[0] : null;

    CoinsetterStreamingConfiguration configuration = new CoinsetterStreamingConfiguration();
    configuration.addAllMarketDataEvents();

    if (customerUuid != null) {
      configuration.addEvent("orders room", customerUuid);
    }

    final Exchange coinsetter = ExchangeFactory.INSTANCE.createExchange(CoinsetterExchange.class.getName());
    final StreamingExchangeService service = coinsetter.getStreamingExchangeService(configuration);

    final Thread consumer = new Thread("consumer") {

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
