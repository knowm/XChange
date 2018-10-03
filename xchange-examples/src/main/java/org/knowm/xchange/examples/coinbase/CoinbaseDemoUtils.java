package org.knowm.xchange.examples.coinbase;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbase.CoinbaseExchange;

/** @author jamespedwards42 */
public class CoinbaseDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new CoinbaseExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey("");
    exSpec.setSecretKey("");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
