package org.knowm.xchange.mexbt.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexbt.MeXBTAuthenticated;
import org.knowm.xchange.mexbt.MeXBTExchange;
import org.knowm.xchange.mexbt.service.MeXBTDigest;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public abstract class MeXBTAuthenticatedPollingService extends MeXBTBasePollingService {

  protected final MeXBTAuthenticated meXBTAuthenticated;
  protected final String apiKey;
  protected final SynchronizedValueFactory<Long> nonceFactory;
  protected final MeXBTDigest meXBTDigest;

  protected MeXBTAuthenticatedPollingService(Exchange exchange) {
    super(exchange);
    this.meXBTAuthenticated = RestProxyFactory.createProxy(MeXBTAuthenticated.class,
        (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(MeXBTExchange.PRIVATE_API_URI_KEY));
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.nonceFactory = exchange.getNonceFactory();
    this.meXBTDigest = MeXBTDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

}
