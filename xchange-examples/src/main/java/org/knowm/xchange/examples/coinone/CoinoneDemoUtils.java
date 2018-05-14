package org.knowm.xchange.examples.coinone;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinone.CoinoneExchange;
import org.knowm.xchange.gatecoin.GatecoinExchange;

/** @author sumdeha */
public class CoinoneDemoUtils {
  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new CoinoneExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey("PublicKeyGeneratedUopnLogin");
    exSpec.setSecretKey("SecretKeyGeneratedUopnLogin");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
