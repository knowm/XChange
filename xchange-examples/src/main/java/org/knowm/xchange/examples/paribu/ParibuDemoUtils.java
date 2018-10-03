package org.knowm.xchange.examples.paribu;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.paribu.ParibuExchange;

/** @author semihunaldi */
public class ParibuDemoUtils {
  public static Exchange createExchange() {
    ExchangeSpecification exSpec = new ParibuExchange().getDefaultExchangeSpecification();
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
