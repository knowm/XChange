package org.knowm.xchange.examples.lgo;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.lgo.LgoEnv;

public class LgoExamplesUtils {

  public static Exchange getExchange() {
    ExchangeSpecification spec = LgoEnv.sandboxMarkets();
    spec.setSecretKey("your private key");
    spec.setApiKey("your api key");

    return ExchangeFactory.INSTANCE.createExchange(spec);
  }
}
