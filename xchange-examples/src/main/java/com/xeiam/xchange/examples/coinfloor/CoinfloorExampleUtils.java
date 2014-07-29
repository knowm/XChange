package com.xeiam.xchange.examples.coinfloor;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinfloor.CoinfloorExchange;

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
