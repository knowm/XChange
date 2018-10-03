package org.knowm.xchange.examples.bitflyer;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitflyer.BitflyerExchange;

public class BitflyerDemoUtils {

  public static Exchange createExchange() {

    // Use the factory to get BitFlyer exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitflyerExchange.class.getName());

    ExchangeSpecification bfxSpec = exchange.getDefaultExchangeSpecification();

    bfxSpec.setApiKey("");
    bfxSpec.setSecretKey("");

    exchange.applySpecification(bfxSpec);

    return exchange;
  }
}
