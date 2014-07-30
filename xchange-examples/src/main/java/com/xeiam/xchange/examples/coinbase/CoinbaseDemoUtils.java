package com.xeiam.xchange.examples.coinbase;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.CoinbaseExchange;

/**
 * @author jamespedwards42
 */
public class CoinbaseDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new CoinbaseExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey("");
    exSpec.setSecretKey("");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
