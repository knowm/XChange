package org.knowm.xchange.examples.bitcoinaverage;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitcoinaverage.BitcoinAverageExchange;

public class BitcoinAverageMetaDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(BitcoinAverageExchange.class.getName());
    exchange.remoteInit();

    System.out.println(exchange.getExchangeMetaData().toString());
  }
}
