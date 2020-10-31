package org.knowm.xchange.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.interceptor.InterceptorProvider;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.Interceptor;
import si.mazi.rescu.RestProxyFactoryImpl;

public final class ExchangeRestProxyBuilder<T> {

  private final Class<T> restInterface;
  private final ExchangeSpecification exchangeSpecification;
  private final List<Interceptor> customInterceptors = new ArrayList<>();
  private final List<ClientConfigCustomizer> clientConfigCustomizers = new ArrayList<>();
  private ClientConfig clientConfig;
  private ResilienceRegistries resilienceRegistries;
  private String baseUrl;
  private IRestProxyFactory restProxyFactory = new RestProxyFactoryImpl();

  private ExchangeRestProxyBuilder(
      Class<T> restInterface, ExchangeSpecification exchangeSpecification) {
    this.restInterface = restInterface;
    this.exchangeSpecification = exchangeSpecification;
    this.baseUrl =
        Optional.ofNullable(exchangeSpecification.getSslUri())
            .orElseGet(exchangeSpecification::getPlainTextUri);
  }

  public static <T> ExchangeRestProxyBuilder<T> forInterface(
      Class<T> restInterface, ExchangeSpecification exchangeSpecification) {
    return new ExchangeRestProxyBuilder<>(restInterface, exchangeSpecification)
        .customInterceptors(InterceptorProvider.provide());
  }

  public ExchangeRestProxyBuilder<T> clientConfig(ClientConfig value) {
    this.clientConfig = value;
    return this;
  }

  public ExchangeRestProxyBuilder<T> clientConfigCustomizer(
      ClientConfigCustomizer clientConfigCustomizer) {
    this.clientConfigCustomizers.add(clientConfigCustomizer);
    return this;
  }

  public ExchangeRestProxyBuilder<T> baseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }

  public ExchangeRestProxyBuilder<T> customInterceptor(Interceptor value) {
    this.customInterceptors.add(value);
    return this;
  }

  public ExchangeRestProxyBuilder<T> customInterceptors(Collection<Interceptor> interceptors) {
    customInterceptors.addAll(interceptors);
    return this;
  }

  public ExchangeRestProxyBuilder<T> restProxyFactory(IRestProxyFactory restProxyFactory) {
    this.restProxyFactory = restProxyFactory;
    return this;
  }

  public T build() {
    if (clientConfig == null) {
      clientConfig = createClientConfig(exchangeSpecification);
    }
    if (resilienceRegistries == null) {
      resilienceRegistries = new ResilienceRegistries();
    }
    clientConfigCustomizers.forEach(
        clientConfigCustomizer -> clientConfigCustomizer.customize(clientConfig));
    return restProxyFactory.createProxy(
        restInterface, baseUrl, clientConfig, customInterceptors.toArray(new Interceptor[0]));
  }

  /**
   * Get a ClientConfig object which contains exchange-specific timeout values
   * (<i>httpConnTimeout</i> and <i>httpReadTimeout</i>) if they were present in the
   * ExchangeSpecification of this instance.
   *
   * @return a rescu client config object
   */
  public static ClientConfig createClientConfig(ExchangeSpecification exchangeSpecification) {

    ClientConfig rescuConfig = new ClientConfig(); // create default rescu config

    // set per exchange connection- and read-timeout (if they have been set in the
    // ExchangeSpecification)
    int customHttpConnTimeout = exchangeSpecification.getHttpConnTimeout();
    if (customHttpConnTimeout > 0) {
      rescuConfig.setHttpConnTimeout(customHttpConnTimeout);
    }
    int customHttpReadTimeout = exchangeSpecification.getHttpReadTimeout();
    if (customHttpReadTimeout > 0) {
      rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
    }
    if (exchangeSpecification.getProxyHost() != null) {
      rescuConfig.setProxyHost(exchangeSpecification.getProxyHost());
    }
    if (exchangeSpecification.getProxyPort() != null) {
      rescuConfig.setProxyPort(exchangeSpecification.getProxyPort());
    }
    return rescuConfig;
  }
}
