package com.xeiam.xchange.examples.mintpal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.mintpal.MintPalExchange;

/**
 * @author jamespedwards42
 */
public class MintPalDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(MintPalExchange.class);

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
