package org.knowm.xchange.tradeogre.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.tradeogre.TradeOgreAuthenticated;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ClientConfigUtil;
import si.mazi.rescu.RestProxyFactory;

public class TradeOgreBaseService extends BaseExchangeService implements BaseService {

  protected final TradeOgreAuthenticated tradeOgre;

  protected TradeOgreBaseService(Exchange exchange) {

    super(exchange);

    String apiKey = exchange.getExchangeSpecification().getApiKey();
    String secretKey = exchange.getExchangeSpecification().getSecretKey();

    ClientConfig config = getClientConfig();
    ClientConfigUtil.addBasicAuthCredentials(config, apiKey, secretKey);
    tradeOgre =
        RestProxyFactory.createProxy(
            TradeOgreAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), config);
  }
}
