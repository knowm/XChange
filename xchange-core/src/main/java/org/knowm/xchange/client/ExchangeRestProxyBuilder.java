package org.knowm.xchange.client;

import com.google.common.annotations.Beta;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.resilience.ResilienceInterceptor;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.Interceptor;
import si.mazi.rescu.RestProxyFactory;

public final class ExchangeRestProxyBuilder<T> {

  private final Class<T> restInterface;
  private final ExchangeSpecification exchangeSpecification;
  private ClientConfig clientConfig;
  private ResilienceRegistries resilienceRegistries;
  private Interceptor customInterceptor;

  private ExchangeRestProxyBuilder(
      Class<T> restInterface, ExchangeSpecification exchangeSpecification) {
    this.restInterface = restInterface;
    this.exchangeSpecification = exchangeSpecification;
  }

  public static <T> ExchangeRestProxyBuilder<T> forInterface(
      Class<T> restInterface, ExchangeSpecification exchangeSpecification) {
    return new ExchangeRestProxyBuilder(restInterface, exchangeSpecification);
  }

  public ExchangeRestProxyBuilder<T> clientConfig(ClientConfig value) {
    this.clientConfig = value;
    return this;
  }

  @Beta
  public ExchangeRestProxyBuilder<T> resilienceRegistries(ResilienceRegistries value) {
    this.resilienceRegistries = value;
    return this;
  }

  public ExchangeRestProxyBuilder<T> customInterceptor(Interceptor value) {
    this.customInterceptor = value;
    return this;
  }

  public T build() {
    if (clientConfig == null) {
      clientConfig = createClientConfig(exchangeSpecification);
    }
    if (resilienceRegistries == null) {
      resilienceRegistries = new ResilienceRegistries();
    }
    List<Interceptor> callInterceptors = new ArrayList<>(2);
    callInterceptors.add(new ResilienceInterceptor(resilienceRegistries, exchangeSpecification));
    if (customInterceptor != null) {
      callInterceptors.add(customInterceptor);
    }
    return RestProxyFactory.createProxy(
        restInterface,
        exchangeSpecification.getSslUri(),
        clientConfig,
        callInterceptors.toArray(Interceptor[]::new));
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
