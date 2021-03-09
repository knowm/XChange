package org.knowm.xchange.latoken.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.latoken.LatokenAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ParamsDigest;

public class LatokenBaseService extends BaseExchangeService<Exchange> implements BaseService {

  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  protected final String apiKey;
  protected final LatokenAuthenticated latoken;
  protected final ParamsDigest signatureCreator;

  protected LatokenBaseService(Exchange exchange) {

    super(exchange);
    this.latoken =
        ExchangeRestProxyBuilder.forInterface(
                LatokenAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        LatokenHmacDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
