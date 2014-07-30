package com.xeiam.xchange.blockchain;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;

/**
 * @author Tim Molter
 */
public class BlockchainExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUri("http://blockchain.info");
    exchangeSpecification.setHost("blockchain.info");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Blockchain");
    exchangeSpecification.setExchangeDescription("Blockchain provide an API for accessing the Botcoin Network.");
    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
  }

}
