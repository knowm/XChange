package org.knowm.xchange.gatehub;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatehubTestUtils {
  private static final Logger log = LoggerFactory.getLogger(GatehubTestUtils.class);

  public static Exchange createExchange() {
    ExchangeSpecification exSpec = new GatehubExchange().getDefaultExchangeSpecification();

    // Set your credentials here:
    exSpec.setUserName("user-uuid"); // user UID
    exSpec.setApiKey("wallet-address"); // wallet address
    exSpec.setSecretKey("the-Bearer-token"); // the Bearer token

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}