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
    exSpec.setUserName("12345");
    exSpec.setApiKey("Abcde");
    exSpec.setSecretKey("1234567890abcdef");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
