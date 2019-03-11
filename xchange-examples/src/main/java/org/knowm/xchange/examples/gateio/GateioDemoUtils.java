package org.knowm.xchange.examples.gateio;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gateio.GateioExchange;

public class GateioDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new GateioExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey("");
    exSpec.setSecretKey("");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
