package org.knowm.xchange.bleutrade.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.BleutradeAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BleutradeBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final BleutradeAuthenticated bleutrade;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeBaseService(Exchange exchange) {

    super(exchange);

    this.bleutrade = RestProxyFactory.createProxy(BleutradeAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BleutradeDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

}
