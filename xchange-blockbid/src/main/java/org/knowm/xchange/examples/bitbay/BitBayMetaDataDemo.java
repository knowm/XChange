package org.knowm.xchange.examples.bitbay;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitbay.BitbayExchange;

public class BitBayMetaDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get bitbay exchange API using default settings
    Exchange anx = ExchangeFactory.INSTANCE.createExchange(BitbayExchange.class.getName());

    System.out.println(anx.getExchangeMetaData().toString());
  }
}
