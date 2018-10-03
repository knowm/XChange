package org.knowm.xchange.examples.abucoins;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.abucoins.AbucoinsExchange;

/** Author: bryant_harris */
public class AbucoinsDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(AbucoinsExchange.class);
    exSpec.setSecretKey("");
    exSpec.setApiKey("");
    exSpec.setPassword("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
