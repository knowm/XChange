package org.knowm.xchange.examples.clevercoin;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.clevercoin.CleverCoinExchange;

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
