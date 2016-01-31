package com.xeiam.xchange.cryptofacilities.service.polling;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptofacilities.CryptoFacilitiesAuthenticated;
import com.xeiam.xchange.cryptofacilities.service.CryptoFacilitiesDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

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
