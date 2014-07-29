package com.xeiam.xchange.examples.cryptotrade;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.CryptoTradeExchange;

public class CryptoTradeExampleUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(CryptoTradeExchange.class);
    exSpec.setSecretKey("");
    exSpec.setApiKey("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
