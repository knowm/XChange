package org.knowm.xchange;

import java.util.HashMap;
import java.util.Map;

/**
 * Specification to provide the following to {@link ExchangeFactory}:
 *
 * <ul>
 *   <li>Provision of required exchangeSpecificParameters for creating an {@link Exchange}
 *   <li>Provision of optional exchangeSpecificParameters for additional configuration
 * </ul>
 */
public class ExchangeSpecification {

  private final String exchangeClassName;
  private String exchangeName;
  private String exchangeDescription;
  private String userName;
  private String password;
  private String secretKey;
  private String apiKey;
  private String sslUri;
  private String plainTextUri;
  private String host;
  private int port = 80;
  private String proxyHost;
  private Integer proxyPort;
  private int httpConnTimeout = 0; // default rescu configuration will be used if value not changed
  private int httpReadTimeout = 0; // default rescu configuration will be used if value not changed
  private ResilienceSpecification resilience = new ResilienceSpecification();
  private String metaDataJsonFileOverride = null;
  private boolean shouldLoadRemoteMetaData = true; // default value
  /** arbitrary exchange params that can be set for unique cases */
  private Map<String, Object> exchangeSpecificParameters = new HashMap<>();

  /**
   * Dynamic binding
   *
   * @param exchangeClassName The exchange class name (e.g.
   *     "org.knowm.xchange.mtgox.v1.MtGoxExchange")
   */
  public ExchangeSpecification(String exchangeClassName) {

    this.exchangeClassName = exchangeClassName;
  }

  /**
   * Static binding
   *
   * @param exchangeClass The exchange class
   */
  public ExchangeSpecification(Class<? extends Exchange> exchangeClass) {

    this.exchangeClassName = exchangeClass.getCanonicalName();
  }

  /** @return The exchange class name for loading at runtime */
  public String getExchangeClassName() {

    return exchangeClassName;
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

  /**
   * Get the host name of the server providing data (e.g. "mtgox.com").
   *
   * @return the host name
   */
  public String getHost() {

    return host;
  }

  /**
   * Set the host name of the server providing data.
   *
   * @param host the host name
   */
  public void setHost(String host) {

    this.host = host;
  }

  /**
   * Get the host name of the http proxy server (e.g. "proxy.com").
   *
   * @return the proxy host name
   */
  public String getProxyHost() {

    return proxyHost;
  }

  /**
   * Set the host name of the http proxy server.
   *
   * @param proxyHost the proxy host name
   */
  public void setProxyHost(String proxyHost) {

    this.proxyHost = proxyHost;
  }

  /**
   * Get the port of the http proxy server (e.g. "80").
   *
   * @return the http proxy port
   */
  public Integer getProxyPort() {

    return proxyPort;
  }

  /**
   * Get the port of the http proxy server.
   *
   * @param proxyPort the host name
   */
  public void setProxyPort(Integer proxyPort) {

    this.proxyPort = proxyPort;
  }

  /**
   * Get the API key. For MtGox this would be the "Rest-Key" field.
   *
   * @return the API key
   */
  public String getApiKey() {

    return apiKey;
  }

  /**
   * Set the API key. For MtGox this would be the "Rest-Key" field.
   *
   * @param apiKey the API key
   */
  public void setApiKey(String apiKey) {

    this.apiKey = apiKey;
  }

  /**
   * Get the port number of the server providing direct socket data (e.g. "1337").
   *
   * @return the port number
   */
  public int getPort() {

    return port;
  }

  /**
   * Set the port number of the server providing direct socket data (e.g. "1337").
   *
   * @param port the port number
   */
  public void setPort(int port) {

    this.port = port;
  }

  /**
   * Get the http connection timeout for the connection. If the default value of zero is returned
   * then the default rescu timeout will be applied. Check the exchange code to see if this option
   * has been implemented.
   *
   * @return the http read timeout in milliseconds
   */
  public int getHttpConnTimeout() {

    return httpConnTimeout;
  }

  /**
   * Set the http connection timeout for the connection. If not supplied the default rescu timeout
   * will be used. Check the exchange code to see if this option has been implemented. (This value
   * can also be set globally in "rescu.properties" by setting the property
   * "rescu.http.connTimeoutMillis".)
   *
   * @param milliseconds the http read timeout in milliseconds
   */
  public void setHttpConnTimeout(int milliseconds) {

    this.httpConnTimeout = milliseconds;
  }

  /**
   * Get the http read timeout for the connection. If the default value of zero is returned then the
   * default rescu timeout will be applied. Check the exchange code to see if this option has been
   * implemented.
   *
   * @return the http read timeout in milliseconds
   */
  public int getHttpReadTimeout() {

    return httpReadTimeout;
  }

  /**
   * Set the http read timeout for the connection. If not supplied the default rescu timeout will be
   * used. Check the exchange code to see if this option has been implemented. (This value can also
   * be set globally in "rescu.properties" by setting the property "rescu.http.readTimeoutMillis".)
   *
   * @param milliseconds the http read timeout in milliseconds
   */
  public void setHttpReadTimeout(int milliseconds) {

    this.httpReadTimeout = milliseconds;
  }

  /**
   * Get the API secret key typically used in HMAC signing of requests. For MtGox this would be the
   * "Rest-Sign" field.
   *
   * @return the secret key
   */
  public String getSecretKey() {

    return secretKey;
  }

  /**
   * Set the API secret key typically used in HMAC signing of requests. For MtGox this would be the
   * "Rest-Sign" field.
   *
   * @param secretKey the secret key
   */
  public void setSecretKey(String secretKey) {

    this.secretKey = secretKey;
  }

  /**
   * Get the URI to reach the <b>root</b> of the exchange API for SSL queries (e.g. use
   * "https://example.com:8443/exchange", not "https://example.com:8443/exchange/api/v3/trades").
   *
   * @return the SSL URI
   */
  public String getSslUri() {

    return sslUri;
  }

  /**
   * Set the URI to reach the <b>root</b> of the exchange API for SSL queries (e.g. use
   * "https://example.com:8443/exchange", not "https://example.com:8443/exchange/api/v3/trades").
   *
   * @param uri the SSL URI
   */
  public void setSslUri(String uri) {

    this.sslUri = uri;
  }

  /**
   * Get the URI to reach the <b>root</b> of the exchange API for plaintext (non-SSL) queries (e.g.
   * use "http://example.com:8080/exchange", not "http://example.com:8080/exchange/api/v3/trades")
   *
   * @return the plain text URI
   */
  public String getPlainTextUri() {

    return plainTextUri;
  }

  /**
   * Set the URI to reach the <b>root</b> of the exchange API for plaintext (non-SSL) queries (e.g.
   * use "http://example.com:8080/exchange", not "http://example.com:8080/exchange/api/v3/trades")
   *
   * @param plainTextUri the plain text URI
   */
  public void setPlainTextUri(String plainTextUri) {

    this.plainTextUri = plainTextUri;
  }

  /**
   * Get the arbitrary exchange-specific parameters to be passed to the exchange implementation.
   *
   * @return a Map of named exchange-specific parameter values
   */
  public Map<String, Object> getExchangeSpecificParameters() {

    return exchangeSpecificParameters;
  }

  /**
   * Set the arbitrary exchange-specific parameters to be passed to the exchange implementation.
   *
   * @param exchangeSpecificParameters a Map of named exchange-specific parameter values
   */
  public void setExchangeSpecificParameters(Map<String, Object> exchangeSpecificParameters) {

    this.exchangeSpecificParameters = exchangeSpecificParameters;
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

  /**
   * Get the password for authentication.
   *
   * @return the password
   */
  public String getPassword() {

    return password;
  }

  /**
   * Set the password for authentication.
   *
   * @param password the password
   */
  public void setPassword(String password) {

    this.password = password;
  }

  /**
   * Get the username for authentication.
   *
   * @return the username
   */
  public String getUserName() {

    return userName;
  }

  /**
   * Set the username for authentication.
   *
   * @param userName the username
   */
  public void setUserName(String userName) {

    this.userName = userName;
  }

  /**
   * Get the exchange name.
   *
   * @return the exchange name (e.g. "Mt Gox")
   */
  public String getExchangeName() {

    return exchangeName;
  }

  /**
   * Set the exchange name (e.g. "Mt Gox").
   *
   * @param exchangeName the exchange name
   */
  public void setExchangeName(String exchangeName) {

    this.exchangeName = exchangeName;
  }

  /**
   * Get the exchange description (e.g. "Major exchange specialising in USD, EUR, GBP").
   *
   * @return the exchange description
   */
  public String getExchangeDescription() {

    return exchangeDescription;
  }

  /**
   * Set the exchange description (e.g. "Major exchange specialising in USD, EUR, GBP").
   *
   * @param exchangeDescription the exchange description
   */
  public void setExchangeDescription(String exchangeDescription) {

    this.exchangeDescription = exchangeDescription;
  }

  public ResilienceSpecification getResilience() {
    return resilience;
  }

  public void setResilience(ResilienceSpecification resilience) {
    this.resilience = resilience;
  }

  /**
   * Get the override file for generating the {@link org.knowm.xchange.dto.meta.ExchangeMetaData}
   * object. By default, the {@link org.knowm.xchange.dto.meta.ExchangeMetaData} object is loaded at
   * startup from a json file on the classpath with the same name as the name of the exchange as
   * defined in {@link ExchangeSpecification}. With this parameter, you can override that file with
   * a file of your choice located outside of the classpath.
   *
   * @return
   */
  public String getMetaDataJsonFileOverride() {

    return metaDataJsonFileOverride;
  }

  /**
   * Set the override file for generating the {@link org.knowm.xchange.dto.meta.ExchangeMetaData}
   * object. By default, the {@link org.knowm.xchange.dto.meta.ExchangeMetaData} object is loaded at
   * startup from a json file on the classpath with the same name as the name of the exchange as
   * defined in {@link ExchangeSpecification}. With this parameter, you can override that file with
   * a file of your choice located outside of the classpath.
   *
   * @return
   */
  public void setMetaDataJsonFileOverride(String metaDataJsonFileOverride) {

    this.metaDataJsonFileOverride = metaDataJsonFileOverride;
  }

  /**
   * By default, some meta data from the exchange is remotely loaded (if implemented).
   *
   * @return
   */
  public boolean isShouldLoadRemoteMetaData() {

    return shouldLoadRemoteMetaData;
  }

  /**
   * By default, some meta data from the exchange is remotely loaded (if implemented). Here you can
   * set this default behavior.
   *
   * @param shouldLoadRemoteMetaData
   */
  public void setShouldLoadRemoteMetaData(boolean shouldLoadRemoteMetaData) {

    this.shouldLoadRemoteMetaData = shouldLoadRemoteMetaData;
  }

  public static class ResilienceSpecification {
    private boolean retryEnabled = false;
    private boolean rateLimiterEnabled = false;

    /**
     * @see #setRetryEnabled(boolean)
     * @return true if enabled
     */
    public boolean isRetryEnabled() {
      return retryEnabled;
    }

    /**
     * Flag that lets you enable retry functionality if it was implemented for the given exchange.
     *
     * <p>If this featrue is implemented and enabled then operations that can be safely retried on
     * socket failures and timeouts will be retried.
     */
    public void setRetryEnabled(boolean retryEnabled) {
      this.retryEnabled = retryEnabled;
    }

    /**
     * @see #setRetryEnabled(boolean)
     * @return true if enabled
     */
    public boolean isRateLimiterEnabled() {
      return rateLimiterEnabled;
    }

    /**
     * Flag that lets you enable call rate limiting functionality if it was implemented for the
     * given exchange.
     *
     * <p>If this featrue is implemented and enabled then we will limit the amount of calls to the
     * exchanges API to not exceeds its limits. This will result in delaying some calls or throwing
     * a {@link io.github.resilience4j.ratelimiter.RequestNotPermitted} exception if we would have
     * to wait to long.
     */
    public void setRateLimiterEnabled(boolean rateLimiterEnabled) {
      this.rateLimiterEnabled = rateLimiterEnabled;
    }
  }
}
