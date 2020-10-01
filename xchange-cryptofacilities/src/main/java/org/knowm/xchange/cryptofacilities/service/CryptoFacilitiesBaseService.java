package org.knowm.xchange.cryptofacilities.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.cryptofacilities.CryptoFacilitiesAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

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
        ExchangeRestProxyBuilder.forInterface(
                CryptoFacilitiesAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator =
        CryptoFacilitiesDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
