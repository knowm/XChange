package org.knowm.xchange.abucoins.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author timmolter
 */
public class AbucoinsBaseService extends BaseExchangeService implements BaseService {

  protected final AbucoinsAuthenticated cexIOAuthenticated;
  protected final AbucoinsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsBaseService(Exchange exchange) {

    super(exchange);

    cexIOAuthenticated = RestProxyFactory.createProxy(AbucoinsAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    signatureCreator = AbucoinsDigest.createInstance(
        exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(),
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getNonceFactory()
    );

  }
}
