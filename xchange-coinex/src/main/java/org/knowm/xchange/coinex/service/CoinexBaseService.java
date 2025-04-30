package org.knowm.xchange.coinex.service;

import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinex.Coinex;
import org.knowm.xchange.coinex.CoinexAuthenticated;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.config.CoinexJacksonObjectMapperFactory;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class CoinexBaseService extends BaseExchangeService<CoinexExchange> implements BaseService {

  protected final String apiKey;
  protected final Coinex coinex;
  protected final CoinexAuthenticated coinexAuthenticated;
  protected final CoinexV2Digest coinexV2ParamsDigest;

  public CoinexBaseService(CoinexExchange exchange) {
    super(exchange);
    coinex =
        ExchangeRestProxyBuilder.forInterface(Coinex.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new CoinexJacksonObjectMapperFactory()))
            .build();
    coinexAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                CoinexAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new CoinexJacksonObjectMapperFactory()))
            .build();
    apiKey = exchange.getExchangeSpecification().getApiKey();

    coinexV2ParamsDigest =
        CoinexV2Digest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
