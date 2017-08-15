package org.knowm.xchange.examples.hitbtc;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.hitbtc.HitbtcExchange;

public class HitbtcExampleUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(HitbtcExchange.class);
    exchangeSpecification.setApiKey("4ac1e9b53c64980c0a3c464c7d9262fc");
    exchangeSpecification.setSecretKey("d03dea317063b7cf4c74ae212735e948");

    return ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }
}
