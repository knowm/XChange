package org.knowm.xchange.gdax.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.gdax.GDAX;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class GDAXBasePollingService<T extends GDAX> extends BaseExchangeService implements BasePollingService {

  protected final T coinbaseEx;
  protected final ParamsDigest digest;

  protected final String apiKey;
  protected final String passphrase;

  protected GDAXBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);
    this.coinbaseEx = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
    this.digest = GDAXDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.passphrase = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase");
  }

  protected String getTimestamp() {
    return String.valueOf(System.currentTimeMillis() / 1000);
  }
}
