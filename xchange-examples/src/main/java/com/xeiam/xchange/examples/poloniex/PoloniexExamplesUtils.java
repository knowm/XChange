package com.xeiam.xchange.examples.poloniex;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.poloniex.PoloniexExchange;

/**
 * @author Zach Holmes
 */

public class PoloniexExamplesUtils {

  public static Exchange getExchange() {

    ExchangeSpecification spec = new ExchangeSpecification(PoloniexExchange.class);
    spec.setApiKey("void");
    spec.setSecretKey("void");

    return ExchangeFactory.INSTANCE.createExchange(spec);
  }
}
