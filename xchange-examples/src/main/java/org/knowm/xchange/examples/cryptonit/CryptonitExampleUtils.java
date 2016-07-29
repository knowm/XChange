package org.knowm.xchange.examples.cryptonit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptonit.v2.CryptonitExchange;

public class CryptonitExampleUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(CryptonitExchange.class);
    exSpec.setSecretKey("");
    exSpec.setApiKey("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
