package org.knowm.xchange.examples.hitbtc;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.hitbtc.HitbtcExchange;

public class HitbtcExampleUtils {

  private static final String APIK_KEY_KEY = "hitbtc_apikey";
  private static final String SECRECT_KEY_KEY = "hitbtc_secrectkey";

  public static Exchange createExchange() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(HitbtcExchange.class);
    exchangeSpecification.setApiKey("");
    exchangeSpecification.setSecretKey("");

    return ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }

  public static Exchange createSecureExchange() {

    String apiKey = System.getenv(APIK_KEY_KEY);
    String secrettKey = System.getenv(SECRECT_KEY_KEY);

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(HitbtcExchange.class);
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secrettKey);

    return ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }

}
