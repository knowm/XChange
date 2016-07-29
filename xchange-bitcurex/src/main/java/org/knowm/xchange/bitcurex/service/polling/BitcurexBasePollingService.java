package org.knowm.xchange.bitcurex.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcurex.Bitcurex;
import org.knowm.xchange.bitcurex.BitcurexAuthenticated;
import org.knowm.xchange.bitcurex.service.BitcurexDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

public class BitcurexBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final BitcurexAuthenticated bitcurexAuthenticated;
  protected final BitcurexDigest signatureCreator;

  protected final Bitcurex bitcurex;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcurexBasePollingService(Exchange exchange) {

    super(exchange);
    this.bitcurex = RestProxyFactory.createProxy(Bitcurex.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = BitcurexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getApiKey());
    this.bitcurexAuthenticated = RestProxyFactory.createProxy(BitcurexAuthenticated.class, exchange.getExchangeSpecification().getSslUri());

  }
}
