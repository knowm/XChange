package org.knowm.xchange;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * Specification to provide the following to {@link ExchangeFactory}:
 *
 * <ul>
 *   <li>Provision of required exchangeSpecificParameters for creating an {@link Exchange}
 *   <li>Provision of optional exchangeSpecificParameters for additional configuration
 * </ul>
 */
@Data
public class ExchangeSpecification {

  /** The exchange class for loading at runtime */
  private final Class<? extends Exchange> exchangeClass;

  private String exchangeName;

  private String exchangeDescription;

  /** Username for authentication. */
  private String userName;

  /** Password for authentication. */
  private String password;

  /** API secret key typically used in HMAC signing of requests */
  private String secretKey;

  private String apiKey;

  /**
   * URI to reach the <b>root</b> of the exchange API for SSL queries (e.g. use
   * "https://example.com:8443/exchange", not "https://example.com:8443/exchange/api/v3/trades").
   */
  private String sslUri;

  /**
   * URI to reach the <b>root</b> of the exchange API for plaintext (non-SSL) queries (e.g. use
   * "http://example.com:8080/exchange", not "http://example.com:8080/exchange/api/v3/trades")
   */
  private String plainTextUri;

  /** Uri that will be used instead of standard exchange websocket uri */
  private String overrideWebsocketApiUri;

  /** Host name of the server providing data */
  private String host;

  /** Port number of the server providing direct socket data */
  private int port = 80;

  /** Host name of the http proxy server (e.g. "proxy.com") */
  private String proxyHost;

  /** Port of the http proxy server (e.g. "80"). */
  private Integer proxyPort;

  /**
   * Http connection timeout for the connection. If not supplied the default rescu timeout will be
   * used. Check the exchange code to see if this option has been implemented. (This value can also
   * be set globally in "rescu.properties" by setting the property "rescu.http.connTimeoutMillis".)
   */
  private int httpConnTimeout;

  /**
   * Http read timeout for the connection. If not supplied the default rescu timeout will be used.
   * Check the exchange code to see if this option has been implemented. (This value can also be set
   * globally in "rescu.properties" by setting the property "rescu.http.readTimeoutMillis".)
   */
  private int httpReadTimeout;

  private ResilienceSpecification resilience = new ResilienceSpecification();

  /**
   * Override file for generating the {@link org.knowm.xchange.dto.meta.ExchangeMetaData} object. By
   * default, the {@link org.knowm.xchange.dto.meta.ExchangeMetaData} object is loaded at startup
   * from a json file on the classpath with the same name as the name of the exchange as defined in
   * {@link ExchangeSpecification}. With this parameter, you can override that file with a file of
   * your choice located outside the classpath.
   */
  private String metaDataJsonFileOverride;

  /** By default, some metadata from the exchange is remotely loaded (if implemented). */
  private boolean shouldLoadRemoteMetaData = true;

  /** arbitrary exchange params that can be set for unique cases */
  private Map<String, Object> exchangeSpecificParameters = new HashMap<>();

  /**
   * Dynamic binding
   *
   * @param exchangeClassName The exchange class name (e.g.
   *     "org.knowm.xchange.mtgox.v1.MtGoxExchange")
   * @deprecated use constructor with exchange class for better performance
   */
  @Deprecated
  public ExchangeSpecification(String exchangeClassName) {
    this(ExchangeClassUtils.exchangeClassForName(exchangeClassName));
  }

  /**
   * Static binding
   *
   * @param exchangeClass The exchange class
   */
  public ExchangeSpecification(Class<? extends Exchange> exchangeClass) {
    this.exchangeClass = exchangeClass;
  }

  /**
   * @return The exchange class name for loading at runtime
   * @see this#getExchangeClass
   * @deprecated use getExchangeClass
   */
  @Deprecated
  public String getExchangeClassName() {
    return exchangeClass.getName();
  }

  /**
   * @param key The key into the parameter map (recommend using the provided standard static
   *     entries)
   * @return Any additional exchangeSpecificParameters that the {@link Exchange} may consume to
   *     configure services
   */
  public Object getParameter(String key) {

    return exchangeSpecificParameters.get(key);
  }

  public int getHttpConnTimeout() {

    return httpConnTimeout;
  }

  /**
   * Get an item from the arbitrary exchange-specific parameters to be passed to the exchange
   * implementation.
   *
   * @return a Map of named exchange-specific parameter values
   */
  public Object getExchangeSpecificParametersItem(String key) {

    return exchangeSpecificParameters.get(key);
  }

  /**
   * Set an item in the arbitrary exchange-specific parameters to be passed to the exchange
   * implementation.
   */
  public void setExchangeSpecificParametersItem(String key, Object value) {

    this.exchangeSpecificParameters.put(key, value);
  }

  @Data
  public static class ResilienceSpecification {

    /**
     * Flag that lets you enable retry functionality if it was implemented for the given exchange.
     *
     * <p>If this feature is implemented and enabled then operations that can be safely retried on
     * socket failures and timeouts will be retried.
     */
    private boolean retryEnabled;

    /**
     * Flag that lets you enable call rate limiting functionality if it was implemented for the
     * given exchange.
     *
     * <p>If this featrue is implemented and enabled then we will limit the amount of calls to the
     * exchanges API to not exceeds its limits. This will result in delaying some calls or throwing
     * a {@link io.github.resilience4j.ratelimiter.RequestNotPermitted} exception if we would have
     * to wait to long.
     */
    private boolean rateLimiterEnabled;
  }
}
