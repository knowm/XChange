package org.knowm.xchange.dsx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DsxAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ClientConfigUtil;
import si.mazi.rescu.RestProxyFactory;

public class DsxBaseService extends BaseExchangeService implements BaseService {

  protected final DsxAuthenticated dsx;

  protected DsxBaseService(Exchange exchange) {

    super(exchange);

    String apiKey = exchange.getExchangeSpecification().getApiKey();
    String secretKey = exchange.getExchangeSpecification().getSecretKey();

    ClientConfig config = getClientConfig();
    ClientConfigUtil.addBasicAuthCredentials(config, apiKey, secretKey);
    dsx =
        RestProxyFactory.createProxy(
            DsxAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), config);
  }
}
