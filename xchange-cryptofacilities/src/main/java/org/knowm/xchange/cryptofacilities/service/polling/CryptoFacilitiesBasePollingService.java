package org.knowm.xchange.cryptofacilities.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptofacilities.CryptoFacilitiesAuthenticated;
import org.knowm.xchange.cryptofacilities.service.CryptoFacilitiesDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesBasePollingService extends BaseExchangeService implements BasePollingService {

  protected CryptoFacilitiesAuthenticated cryptoFacilities;
  protected ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesBasePollingService(Exchange exchange) {

    super(exchange);

    cryptoFacilities = RestProxyFactory.createProxy(CryptoFacilitiesAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    signatureCreator = CryptoFacilitiesDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

}
