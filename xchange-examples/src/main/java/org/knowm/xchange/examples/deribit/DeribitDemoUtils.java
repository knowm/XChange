package org.knowm.xchange.examples.deribit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.deribit.v2.DeribitExchange;

public class DeribitDemoUtils {

  public static Exchange createExchange() {

    // Use the factory to get Deribit exchange API using default settings
    Exchange deribit = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    ExchangeSpecification deribitSpec = deribit.getDefaultExchangeSpecification();

    // deribitSpec.setApiKey("");
    // deribitSpec.setSecretKey("");

    deribit.applySpecification(deribitSpec);

    return deribit;
  }
}
