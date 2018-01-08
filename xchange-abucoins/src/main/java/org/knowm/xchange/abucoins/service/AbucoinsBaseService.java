package org.knowm.xchange.abucoins.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.Abucoins;
import org.knowm.xchange.abucoins.AbucoinsAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author bryant_harris
 */
public class AbucoinsBaseService extends BaseExchangeService implements BaseService {
  protected final Abucoins abucoins;
  protected final AbucoinsAuthenticated abucoinsAuthenticated;
  protected final AbucoinsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsBaseService(Exchange exchange) {

    super(exchange);

    abucoins = RestProxyFactory.createProxy(Abucoins.class,
                                            exchange.getExchangeSpecification().getSslUri(),
                                            getClientConfig());
    abucoinsAuthenticated = RestProxyFactory.createProxy(AbucoinsAuthenticated.class,
                                                         exchange.getExchangeSpecification().getSslUri());
    signatureCreator = AbucoinsDigest.createInstance(abucoins,
                                                     exchange.getExchangeSpecification().getSecretKey());
  }
}
