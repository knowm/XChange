package com.xeiam.xchange.mexbt.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.mexbt.MeXBTAuthenticated;
import com.xeiam.xchange.mexbt.MeXBTExchange;
import com.xeiam.xchange.mexbt.service.MeXBTDigest;

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
