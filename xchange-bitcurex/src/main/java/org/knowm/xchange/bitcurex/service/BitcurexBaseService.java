package org.knowm.xchange.bitcurex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcurex.Bitcurex;
import org.knowm.xchange.bitcurex.BitcurexAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

public class BitcurexBaseService extends BaseExchangeService implements BaseService {

  protected final BitcurexAuthenticated bitcurexAuthenticated;
  protected final BitcurexDigest signatureCreator;

  protected final Bitcurex bitcurex;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcurexBaseService(Exchange exchange) {

    super(exchange);
    this.bitcurex = RestProxyFactory.createProxy(Bitcurex.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.signatureCreator = BitcurexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getApiKey());
    this.bitcurexAuthenticated = RestProxyFactory.createProxy(BitcurexAuthenticated.class, exchange.getExchangeSpecification().getSslUri(),
        getClientConfig());

  }
}
