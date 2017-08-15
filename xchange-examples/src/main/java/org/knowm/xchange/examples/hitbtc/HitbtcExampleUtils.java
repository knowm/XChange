package org.knowm.xchange.examples.hitbtc;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.hitbtc.HitbtcExchange;

public class HitbtcExampleUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(HitbtcExchange.class);
    exchangeSpecification.setApiKey("");
    exchangeSpecification.setSecretKey("");

    return ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }
}
