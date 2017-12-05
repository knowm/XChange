package org.knowm.xchange.examples.gateio;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bter.BTERExchange;

public class GateioDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new BTERExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey("");
    exSpec.setSecretKey("");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
