package org.knowm.xchange.bybit.service;

import java.util.concurrent.TimeUnit;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.Bybit;
import org.knowm.xchange.bybit.BybitAuthenticated;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseResilientExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class BybitBaseService extends BaseResilientExchangeService<BybitExchange> {

  protected final BybitAuthenticated bybitAuthenticated;
  protected final Bybit bybit;
  protected final ParamsDigest signatureCreator;
  protected final SynchronizedValueFactory<Long> nonceFactory =
      new CurrentTimeIncrementalNonceFactory(TimeUnit.MILLISECONDS);
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
