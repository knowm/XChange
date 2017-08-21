package org.knowm.xchange.examples.hitbtc;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.hitbtc.HitbtcExchange;

public class HitbtcExampleUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(HitbtcExchange.class);
    exSpec.setApiKey("");
    exSpec.setSecretKey("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
