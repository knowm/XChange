package com.xeiam.xchange.examples.clevercoin;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.clevercoin.CleverCoinExchange;

/**
 * @author Karsten Nilsen & Konstantin Indjov
 */
public class CleverCoinDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new CleverCoinExchange().getDefaultExchangeSpecification();
    exSpec.setUserName("username@clevercoin.com");
    exSpec.setApiKey("MuOs1ffnFPCuyxO+iz+ng8NjrxbJO2qb");
    exSpec.setSecretKey("aKxFBb4EZIiNj8vYIPeTimYzxZV7bvAs");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
