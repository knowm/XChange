package com.xeiam.xchange.examples.justcoin;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.justcoin.JustcoinExchange;

public class JustcoinDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(JustcoinExchange.class);

    // API key needed for access rights to specific API calls
    exSpec.setApiKey("");

    // User and password needed for session authentication
    exSpec.setUserName("");
    exSpec.setPassword("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
