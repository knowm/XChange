package com.xeiam.xchange.examples.kraken;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.kraken.KrakenExchange;

public class KrakenExampleUtils {

  private KrakenExampleUtils() {

  }

  public static Exchange createTestExchange() {

    Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
    krakenExchange.getExchangeSpecification().setApiKey("API Key");
    krakenExchange.getExchangeSpecification().setSecretKey("Secret==");
    krakenExchange.getExchangeSpecification().setUserName("user");
    krakenExchange.applySpecification(krakenExchange.getExchangeSpecification());
    return krakenExchange;
  }
}
