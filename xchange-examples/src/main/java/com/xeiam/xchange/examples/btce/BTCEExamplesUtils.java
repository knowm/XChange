package com.xeiam.xchange.examples.btce;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEExchange;

/**
 * @author Matija Mazi
 */
public class BTCEExamplesUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BTCEExchange.class);
    exSpec.setSecretKey("26d17ede9bba11f1a71ab99f5d51041879d89c61e17601e45ea3fdfe1d38e649");
    exSpec.setApiKey("01Q84C7I-1A8T1TY8-LFAK34DS-CS4UHLYZ-RDBM4EOK");
    exSpec.setSslUri("https://btc-e.com");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
