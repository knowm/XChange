package org.knowm.xchange.examples.gateio.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gateio.GateioExchange;

public class GateioMetaDataDemo {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new GateioExchange().getDefaultExchangeSpecification();
    exSpec.setShouldLoadRemoteMetaData(true);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    System.out.println(exchange.getExchangeSpecification().isShouldLoadRemoteMetaData());
    System.out.println(exchange.getExchangeMetaData().toString());
    System.out.println(exchange.getExchangeSymbols());
  }
}
