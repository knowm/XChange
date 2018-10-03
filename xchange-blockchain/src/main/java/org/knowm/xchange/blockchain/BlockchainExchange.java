package org.knowm.xchange.blockchain;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Tim Molter */
public class BlockchainExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUri("https://blockchain.info");
    exchangeSpecification.setHost("blockchain.info");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Blockchain");
    exchangeSpecification.setExchangeDescription(
        "Blockchain provide an API for accessing the Botcoin Network.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }

  @Override
  protected void initServices() {}
}
