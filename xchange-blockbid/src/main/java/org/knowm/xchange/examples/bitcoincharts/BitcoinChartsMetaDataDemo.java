package org.knowm.xchange.examples.bitcoincharts;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitcoincharts.BitcoinChartsExchange;

public class BitcoinChartsMetaDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(BitcoinChartsExchange.class.getName());
    exchange.remoteInit();
    System.out.println(exchange.getExchangeMetaData().toString());
  }
}
