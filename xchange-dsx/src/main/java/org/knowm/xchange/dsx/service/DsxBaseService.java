package org.knowm.xchange.dsx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ClientConfigCustomizer;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.dsx.DsxAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ClientConfigUtil;

public class DsxBaseService extends BaseExchangeService implements BaseService {

  protected final DsxAuthenticated dsx;

  protected DsxBaseService(Exchange exchange) {
    super(exchange);

    String apiKey = exchange.getExchangeSpecification().getApiKey();
    String secretKey = exchange.getExchangeSpecification().getSecretKey();

    ClientConfigCustomizer clientConfigCustomizer =
        config -> ClientConfigUtil.addBasicAuthCredentials(config, apiKey, secretKey);
    dsx =
        ExchangeRestProxyBuilder.forInterface(
                DsxAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
  }
}
