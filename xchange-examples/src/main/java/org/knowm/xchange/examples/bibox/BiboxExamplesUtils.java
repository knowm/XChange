package org.knowm.xchange.examples.bibox;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bibox.BiboxExchange;

public class BiboxExamplesUtils {

  public static Exchange getExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BiboxExchange.class);
    exSpec.setApiKey("your-api-key");
    exSpec.setSecretKey("your-secret-key");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

}
