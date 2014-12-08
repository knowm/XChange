package com.xeiam.xchange.examples.bitstamp;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampExchange;

/**
 * @author Matija Mazi
 */
public class BitstampDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new BitstampExchange().getDefaultExchangeSpecification();
    exSpec.setUserName("34387");
    exSpec.setApiKey("a4SDmpl9s6xWJS5fkKRT6yn41vXuY0AM");
    exSpec.setSecretKey("sisJixU6Xd0d1yr6w02EHCb9UwYzTNuj");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
