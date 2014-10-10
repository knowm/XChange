package com.xeiam.xchange.examples.bitfinex;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexExchange;

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
