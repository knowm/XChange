package org.knowm.xchange.examples.cryptonit2;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptonit2.CryptonitExchange;

/** @author Matija Mazi */
public class CryptonitDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new CryptonitExchange().getDefaultExchangeSpecification();
    exSpec.setUserName("209");
    exSpec.setApiKey("2DEBCF0F-25FF-472E-979C-68EE6FF62A13 ");
    exSpec.setSecretKey("ODNkOTQxZTViYTIyYzU4ZDQ2MGM1MmE3MzI5ZGFhMzk0ZDk5ZjEwMw");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
