package org.knowm.xchange.examples.bter.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bter.BTERExchange;

public class BTERMetaDataDemo {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new BTERExchange().getDefaultExchangeSpecification();
    exSpec.setShouldLoadRemoteMetaData(true);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    System.out.println(exchange.getExchangeSpecification().isShouldLoadRemoteMetaData());
    System.out.println(exchange.getExchangeMetaData().toString());
    System.out.println(exchange.getExchangeSymbols());

  }

}
