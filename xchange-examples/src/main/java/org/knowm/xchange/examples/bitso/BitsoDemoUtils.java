package org.knowm.xchange.examples.bitso;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitso.BitsoExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Matija Mazi */
public class BitsoDemoUtils {
  private static final Logger log = LoggerFactory.getLogger(BitsoDemoUtils.class);

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new BitsoExchange().getDefaultExchangeSpecification();

    log.warn("Bitso credentials must be set in BitsoDemoUtils.java.");

    // Set your credentials here:
    exSpec.setSslUri("https://api-dev.bitso.com");
    exSpec.setHost("dev.api.bitso.com");
    exSpec.setUserName("7895");
    exSpec.setApiKey("berCEsRXxq");
    exSpec.setSecretKey("427d805db613b082bf7f9ef761b7e3e2");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
