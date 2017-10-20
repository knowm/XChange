package org.knowm.xchange.gdax.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.gdax.GDAX;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class GDAXBaseService extends BaseExchangeService implements BaseService {

  protected final GDAX coinbaseEx;
  protected final ParamsDigest digest;

  protected final String apiKey;
  protected final String passphrase;

  protected GDAXBaseService(Exchange exchange) {

    super(exchange);
    this.coinbaseEx = RestProxyFactory.createProxy(GDAX.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.digest = GDAXDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.passphrase = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase");
  }

}
