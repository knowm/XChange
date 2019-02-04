package org.knowm.xchange.coinmarketcap.pro.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.CoinMarketCapAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class CoinMarketCapBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final CoinMarketCapAuthenticated coinMarketCapAuthenticated;

  protected CoinMarketCapBaseService(Exchange exchange) {
    super(exchange);

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.coinMarketCapAuthenticated =
        RestProxyFactory.createProxy(
            CoinMarketCapAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
  }
}
