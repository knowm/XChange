package org.knowm.xchange.bybit.service;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.Bybit;
import org.knowm.xchange.bybit.BybitAuthenticated;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ProxyConfig;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class BybitBaseService implements BaseService {

  protected final BybitAuthenticated bybitAuthenticated;
  protected final Bybit bybit;
  protected final ParamsDigest signatureCreator;
  protected final SynchronizedValueFactory<Long> nonceFactory =
      new CurrentTimeIncrementalNonceFactory(TimeUnit.MILLISECONDS);
  protected final String apiKey;

  @SneakyThrows
  public BybitBaseService(Exchange exchange) {
    bybit =
        ExchangeRestProxyBuilder.forInterface(Bybit.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BybitJacksonObjectMapperFactory()))
            .restProxyFactory(
                ProxyConfig.getInstance().getRestProxyFactoryClass().getDeclaredConstructor().newInstance())
            .build();
    bybitAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BybitAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BybitJacksonObjectMapperFactory()))
            .restProxyFactory(
                ProxyConfig.getInstance().getRestProxyFactoryClass().getDeclaredConstructor().newInstance())
            .build();
    signatureCreator =
        BybitDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
  }
}
