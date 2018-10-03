package org.knowm.xchange.cexio.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

/** @author timmolter */
public class CexIOBaseService extends BaseExchangeService implements BaseService {

  protected final CexIOAuthenticated cexIOAuthenticated;
  protected final CexIODigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOBaseService(Exchange exchange) {

    super(exchange);

    cexIOAuthenticated =
        RestProxyFactory.createProxy(
            CexIOAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    signatureCreator =
        CexIODigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory());
  }
}
