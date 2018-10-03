package org.knowm.xchange.examples.poloniex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.poloniex.PoloniexExchange;

/** @author Zach Holmes */
public class PoloniexExamplesUtils {

  public static Exchange getExchange() {

    ExchangeSpecification spec = new ExchangeSpecification(PoloniexExchange.class);
    spec.setApiKey("your-api-key-here");
    spec.setSecretKey("your-api-key-here");

    return ExchangeFactory.INSTANCE.createExchange(spec);
  }
}
