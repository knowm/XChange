package org.knowm.xchange.examples.gateio;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.gateio.GateioExchange;

public class GateioExchangeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GateioExchange.class.getName());

    System.out.println("ExchangeMetaData toString(): " + exchange.getExchangeMetaData().toString());
    System.out.println(
        "ExchangeMetaData toJSONString(): " + exchange.getExchangeMetaData().toJSONString());
    System.out.println("Currency Pairs: " + exchange.getExchangeSymbols());
  }
}
