package com.xeiam.xchange.examples.bitso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitso.BitsoExchange;

/**
 * @author Matija Mazi
 */
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
