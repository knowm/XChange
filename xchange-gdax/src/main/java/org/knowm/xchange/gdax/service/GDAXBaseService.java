package org.knowm.xchange.gdax.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.gdax.GDAX;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class GDAXBaseService<T extends GDAX> extends BaseExchangeService implements BaseService {

  protected final T coinbaseEx;
  protected final ParamsDigest digest;

  protected final String apiKey;
  protected final String passphrase;

  protected GDAXBaseService(Class<T> type, Exchange exchange) {

    super(exchange);

    // allow HTTP connect- and read-timeout to be set per exchange
    ClientConfig rescuConfig = new ClientConfig(); // default rescu config
    int customHttpConnTimeout = exchange.getExchangeSpecification().getHttpConnTimeout();
    if (customHttpConnTimeout > 0) {
      rescuConfig.setHttpConnTimeout(customHttpConnTimeout);
    }
    int customHttpReadTimeout = exchange.getExchangeSpecification().getHttpReadTimeout();
    if (customHttpReadTimeout > 0) {
      rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
    }

    this.coinbaseEx = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri(), rescuConfig);
    this.digest = GDAXDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.passphrase = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase");
  }

}
