package org.knowm.xchange.examples.koinim;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.koinim.KoinimExchange;

/** @author ahmetoz */
public class KoinimDemoUtils {
  public static Exchange createExchange() {
    ExchangeSpecification exSpec = new KoinimExchange().getDefaultExchangeSpecification();
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
