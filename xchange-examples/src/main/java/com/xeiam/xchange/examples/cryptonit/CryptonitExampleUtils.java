package com.xeiam.xchange.examples.cryptonit;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptonit.v2.CryptonitExchange;

public class CryptonitExampleUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(CryptonitExchange.class);
    exSpec.setSecretKey("");
    exSpec.setApiKey("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
