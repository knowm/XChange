package org.knowm.xchange.examples.gatecoin;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gatecoin.GatecoinExchange;

/** @author sumdeha */
public class GatecoinDemoUtils {
  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new GatecoinExchange().getDefaultExchangeSpecification();
    exSpec.setUserName("username");
    exSpec.setApiKey("PublicKeyGeneratedUopnLogin");
    exSpec.setSecretKey("SecretKeyGeneratedUopnLogin");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
