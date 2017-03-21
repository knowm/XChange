package org.knowm.xchange.bittrex.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.v1.BittrexAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BittrexBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final BittrexAuthenticated bittrexAuthenticated;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexBaseService(Exchange exchange) {

    super(exchange);

    this.bittrexAuthenticated = RestProxyFactory.createProxy(BittrexAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BittrexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

}
