package org.knowm.xchange.examples.coingi;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coingi.CoingiExchange;

public class CoingiDemoUtils {
  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new CoingiExchange().getDefaultExchangeSpecification();
    exSpec.setUserName("Username");
    exSpec.setApiKey("PublicKeyGeneratedUponLogin");
    exSpec.setSecretKey("SecretKeyGeneratedUponLogin");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
