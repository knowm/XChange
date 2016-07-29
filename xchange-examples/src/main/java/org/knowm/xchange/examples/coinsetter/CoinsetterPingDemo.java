package org.knowm.xchange.examples.coinsetter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterPingServiceRaw;

public class CoinsetterPingDemo {

  private static final Logger log = LoggerFactory.getLogger(CoinsetterPingDemo.class);

  public static void main(String[] args) throws IOException {

    Exchange coinsetter = CoinsetterExamplesUtils.getExchange();
    CoinsetterPingServiceRaw pingService = new CoinsetterPingServiceRaw(coinsetter);

    String text = pingService.ping("HelloWorld");
    log.info("ping result: {}", text);
  }

}
