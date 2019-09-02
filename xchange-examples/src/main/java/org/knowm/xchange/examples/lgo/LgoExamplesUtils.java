package org.knowm.xchange.examples.lgo;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.lgo.LgoEnv;

public class LgoExamplesUtils {

  public static Exchange getExchange() {
    ExchangeSpecification spec = LgoEnv.sandbox();
    spec.setSecretKey("your private key");
    spec.setApiKey("your api key");

    return ExchangeFactory.INSTANCE.createExchange(spec);
  }
}
