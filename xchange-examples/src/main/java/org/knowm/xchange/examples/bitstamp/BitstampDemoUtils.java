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
    exSpec.setApiKey("a4SDmpl9s6xWJS5fkKRT6yn41vXuY0AM");
    exSpec.setSecretKey("sisJixU6Xd0d1yr6w02EHCb9UwYzTNuj");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
