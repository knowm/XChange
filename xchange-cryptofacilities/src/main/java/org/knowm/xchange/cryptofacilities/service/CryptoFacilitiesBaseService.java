package org.knowm.xchange.cryptofacilities.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptofacilities.CryptoFacilitiesAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesBaseService extends BaseExchangeService implements BaseService {

  protected CryptoFacilitiesAuthenticated cryptoFacilities;
  protected ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesBaseService(Exchange exchange) {

    super(exchange);

    cryptoFacilities =
        RestProxyFactory.createProxy(
            CryptoFacilitiesAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    signatureCreator =
        CryptoFacilitiesDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
