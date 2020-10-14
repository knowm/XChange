package org.knowm.xchange.bl3p.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bl3p.Bl3pAuthenticated;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class Bl3pBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final Bl3pAuthenticated bl3p;
  protected final SynchronizedValueFactory<Long> nonceFactory;
  protected final ParamsDigest signatureCreator;

  public Bl3pBaseService(Exchange exchange) {
    super(exchange);

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.bl3p =
        ExchangeRestProxyBuilder.forInterface(
                Bl3pAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.nonceFactory = this.exchange.getNonceFactory();
    this.signatureCreator =
        Bl3pDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
