package org.knowm.xchange.examples.bitfinex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;

public class BitfinexDemoUtils {

  public static Exchange createExchange() {

    // Use the factory to get BFX exchange API using default settings
    Exchange bfx = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());

    ExchangeSpecification bfxSpec = bfx.getDefaultExchangeSpecification();

    bfxSpec.setApiKey("");
    bfxSpec.setSecretKey("");

    bfx.applySpecification(bfxSpec);

    return bfx;
  }
}
