package org.knowm.xchange.examples.bitmex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.BitmexExchange;

public class BitmexDemoUtils {

  public static Exchange createExchange() {

    // Use the factory to get Bitmex exchange API using default settings
    Exchange bitmex = ExchangeFactory.INSTANCE.createExchange(BitmexExchange.class.getName());

    ExchangeSpecification bitmexSpec = bitmex.getDefaultExchangeSpecification();

    // bitmexSpec.setApiKey("");
    // bitmexSpec.setSecretKey("");

    bitmex.applySpecification(bitmexSpec);

    return bitmex;
  }
}
