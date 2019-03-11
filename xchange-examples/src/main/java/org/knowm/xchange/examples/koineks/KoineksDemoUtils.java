package org.knowm.xchange.examples.koineks;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.koineks.KoineksExchange;

/** @author semihunaldi */
public class KoineksDemoUtils {
  public static Exchange createExchange() {
    ExchangeSpecification exSpec = new KoineksExchange().getDefaultExchangeSpecification();
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
