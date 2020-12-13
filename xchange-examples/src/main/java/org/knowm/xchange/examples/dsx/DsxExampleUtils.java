package org.knowm.xchange.examples.dsx;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dsx.DsxExchange;

public class DsxExampleUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(DsxExchange.class);
    exSpec.setApiKey("");
    exSpec.setSecretKey("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
