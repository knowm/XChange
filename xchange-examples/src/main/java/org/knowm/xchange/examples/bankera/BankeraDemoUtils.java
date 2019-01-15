package org.knowm.xchange.examples.bankera;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bankera.BankeraExchange;

public class BankeraDemoUtils {
  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BankeraExchange.class);

    exSpec.setExchangeSpecificParametersItem("clientId", "");
    exSpec.setExchangeSpecificParametersItem("clientSecret", "");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
