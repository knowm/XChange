package org.knowm.xchange.examples.kraken;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.kraken.KrakenExchange;

public class KrakenExampleUtils {

  private KrakenExampleUtils() {}

  public static Exchange createTestExchange() {

    Exchange krakenExchange =
        ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
    krakenExchange.getExchangeSpecification().setApiKey("API Key");
    krakenExchange.getExchangeSpecification().setSecretKey("Secret==");
    krakenExchange.getExchangeSpecification().setUserName("user");
    krakenExchange.applySpecification(krakenExchange.getExchangeSpecification());
    return krakenExchange;
  }
}
