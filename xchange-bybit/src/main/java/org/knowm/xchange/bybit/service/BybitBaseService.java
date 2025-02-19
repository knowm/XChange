package org.knowm.xchange.bybit.service;

import org.knowm.xchange.bybit.Bybit;
import org.knowm.xchange.bybit.BybitAuthenticated;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.service.BaseResilientExchangeService;
import si.mazi.rescu.ParamsDigest;

public class BybitBaseService extends BaseResilientExchangeService<BybitExchange> {

  protected final BybitAuthenticated bybitAuthenticated;
  protected final Bybit bybit;
  protected final ParamsDigest signatureCreator;

  protected final String apiKey;

  protected BybitBaseService(BybitExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
    bybit =
        ExchangeRestProxyBuilder.forInterface(Bybit.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BybitJacksonObjectMapperFactory()))
            .build();
    bybitAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BybitAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BybitJacksonObjectMapperFactory()))
            .build();
    signatureCreator =
        BybitDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
  }

}
