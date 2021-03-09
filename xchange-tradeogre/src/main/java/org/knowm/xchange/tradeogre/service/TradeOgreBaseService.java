package org.knowm.xchange.tradeogre.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ClientConfigCustomizer;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.tradeogre.TradeOgreAuthenticated;
import si.mazi.rescu.ClientConfigUtil;

public class TradeOgreBaseService extends BaseExchangeService implements BaseService {

  protected final TradeOgreAuthenticated tradeOgre;

  protected TradeOgreBaseService(Exchange exchange) {

    super(exchange);

    String apiKey = exchange.getExchangeSpecification().getApiKey();
    String secretKey = exchange.getExchangeSpecification().getSecretKey();

    ClientConfigCustomizer clientConfigCustomizer =
        config -> ClientConfigUtil.addBasicAuthCredentials(config, apiKey, secretKey);
    tradeOgre =
        ExchangeRestProxyBuilder.forInterface(
                TradeOgreAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
  }
}
