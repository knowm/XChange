package com.xeiam.xchange.examples.hitbtc;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.hitbtc.HitbtcExchange;

public class HitbtcExampleUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(HitbtcExchange.class);
    exSpec.setApiKey("");
    exSpec.setSecretKey("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
