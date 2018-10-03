package org.knowm.xchange.examples.btcmarkets;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcmarkets.BTCMarketsExchange;

public class BTCMarketsExampleUtils {

  private BTCMarketsExampleUtils() {}

  public static Exchange createTestExchange() {
    Exchange btcMarketsExchange =
        ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getName());
    ExchangeSpecification spec = btcMarketsExchange.getExchangeSpecification();

    // Set your actual credentials here for the demos to work.
    spec.setApiKey("<Put your API key here.>");
    spec.setSecretKey("<Put your secret key here.>");

    btcMarketsExchange.applySpecification(spec);
    return btcMarketsExchange;
  }
}
