package org.knowm.xchange.cexio.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAuthenticated;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

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
        ExchangeRestProxyBuilder.forInterface(
                CexIOAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator =
        CexIODigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory());
  }
}
