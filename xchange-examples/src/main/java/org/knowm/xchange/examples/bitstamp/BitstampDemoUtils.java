package org.knowm.xchange.examples.bitstamp;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampExchange;

/** @author Matija Mazi */
public class BitstampDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new BitstampExchange().getDefaultExchangeSpecification();
    exSpec.setUserName("34387");
    exSpec.setApiKey("EjZ8Cyk9TJO6j2mp7jmtB9NGA3f6FCtV");
    exSpec.setSecretKey("X2pbzy3gFdzqwujCNgYxTcEmprGQNz0G");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
