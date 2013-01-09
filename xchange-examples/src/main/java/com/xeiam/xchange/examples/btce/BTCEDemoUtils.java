package com.xeiam.xchange.examples.btce;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.BTCEExchange;

/**
 * @author Matija Mazi <br/>
 */
public class BTCEDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BTCEExchange.class);
    exSpec.setSecretKey("4df0c1438aee12cd04be9d50bae23b4b4245c3ac9b37993908411c4d3023af77");
    exSpec.setApiKey("82FE1OI8-6KVWGK2L-GFR3UETO-Y3G3NZQ7-0QISCMTU");
    exSpec.setUri("https://btc-e.com");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
