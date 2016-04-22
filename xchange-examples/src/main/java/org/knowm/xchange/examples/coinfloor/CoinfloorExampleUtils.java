package org.knowm.xchange.examples.coinfloor;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinfloor.CoinfloorExchange;

/**
 * @author obsessiveOrange
 */
public class CoinfloorExampleUtils {

  /**
   * @return
   */
  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(CoinfloorExchange.class);
    exSpec.setPlainTextUriStreaming("ws://api.coinfloor.co.uk");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

}
