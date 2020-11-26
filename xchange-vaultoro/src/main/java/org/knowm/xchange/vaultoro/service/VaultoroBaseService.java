package org.knowm.xchange.vaultoro.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.vaultoro.VaultoroAuthenticated;
import si.mazi.rescu.ParamsDigest;

public class VaultoroBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final VaultoroAuthenticated vaultoro;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroBaseService(Exchange exchange) {

    super(exchange);

    this.vaultoro =
        ExchangeRestProxyBuilder.forInterface(
                VaultoroAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        VaultoroDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
