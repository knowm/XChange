package org.knowm.xchange.examples.bithumb;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bithumb.BithumbExchange;

public class BithumbDemoUtils {
  public static Exchange createExchange() {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BithumbExchange.class);

    ExchangeSpecification bithumbSpec = exchange.getDefaultExchangeSpecification();

    bithumbSpec.setApiKey("");
    bithumbSpec.setSecretKey("");

    exchange.applySpecification(bithumbSpec);

    return exchange;
  }
}
