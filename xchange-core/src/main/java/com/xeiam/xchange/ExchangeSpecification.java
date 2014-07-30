package com.xeiam.xchange;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Specification to provide the following to {@link ExchangeFactory}:
 * </p>
 * <ul>
 * <li>Provision of required exchangeSpecificParameters for creating an {@link Exchange}</li>
 * <li>Provision of optional exchangeSpecificParameters for additional configuration</li>
 * </ul>
 */
public class ExchangeSpecification {

  private String exchangeName;

  private String exchangeDescription;

  private String userName;

  private String password;

  private String secretKey;

  private String apiKey;

  private String sslUri;

  private String plainTextUri;

  private String sslUriStreaming;

  private String plainTextUriStreaming;

  private String host;

  private int port = 80;

  private final String exchangeClassName;

  /** arbitrary exchange params that can be set for unique cases */
  private Map<String, Object> exchangeSpecificParameters = new HashMap<String, Object>();

  /**
   * Dynamic binding
   * 
   * @param exchangeClassName The exchange class name (e.g. "com.xeiam.xchange.mtgox.v1.MtGoxExchange")
   */
  public ExchangeSpecification(String exchangeClassName) {

    this.exchangeClassName = exchangeClassName;
  }

  /**
   * Static binding
   * 
   * @param exchangeClass The exchange class
   */
  public ExchangeSpecification(Class exchangeClass) {

    this.exchangeClassName = exchangeClass.getCanonicalName();
  }

  /**
   * @return The exchange class name for loading at runtime
   */
  public String getExchangeClassName() {

    return exchangeClassName;
  }

  /**
   * @param key The key into the parameter map (recommend using the provided standard static entries)
   * @return Any additional exchangeSpecificParameters that the {@link Exchange} may consume to configure services
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
   * Get the API secret key typically used in HMAC signing of requests. For MtGox this would be the "Rest-Sign" field.
   * 
   * @return the secret key
   */
  public String getSecretKey() {

    return secretKey;
  }

  /**
   * Set the API secret key typically used in HMAC signing of requests. For MtGox this would be the "Rest-Sign" field.
   * 
   * @param secretKey the secret key
   */
  public void setSecretKey(String secretKey) {

    this.secretKey = secretKey;
  }

  /**
   * Get the URI to reach the <b>root</b> of the exchange API for SSL queries
   * (e.g. use "https://example.com:8443/exchange", not "https://example.com:8443/exchange/api/v3/trades").
   * 
   * @return the SSL URI
   */
  public String getSslUri() {

    return sslUri;
  }

  /**
   * Set the URI to reach the <b>root</b> of the exchange API for SSL queries
   * (e.g. use "https://example.com:8443/exchange", not "https://example.com:8443/exchange/api/v3/trades").
   * 
   * @param uri the SSL URI
   */
  public void setSslUri(String uri) {

    this.sslUri = uri;
  }

  /**
   * Get the URI to reach the <b>root</b> of the exchange API for plaintext (non-SSL) queries
   * (e.g. use "http://example.com:8080/exchange", not "http://example.com:8080/exchange/api/v3/trades")
   * 
   * @return the plain text URI
   */
  public String getPlainTextUri() {

    return plainTextUri;
  }

  /**
   * Set the URI to reach the <b>root</b> of the exchange API for plaintext (non-SSL) queries
   * (e.g. use "http://example.com:8080/exchange", not "http://example.com:8080/exchange/api/v3/trades")
   * 
   * @param plainTextUri the plain text URI
   */
  public void setPlainTextUri(String plainTextUri) {

    this.plainTextUri = plainTextUri;
  }

  /**
   * Set the URI for plain text streaming.
   * 
   * @return the plaintext streaming URI
   */
  public String getPlainTextUriStreaming() {

    return plainTextUriStreaming;
  }

  /**
   * Set the URI for plain text streaming.
   * 
   * @param plainTextUriStreaming the plaintext streaming URI
   */
  public void setPlainTextUriStreaming(String plainTextUriStreaming) {

    this.plainTextUriStreaming = plainTextUriStreaming;
  }

  /**
   * Get the URI for SSL streaming.
   * 
   * @return the URI for ssl streaming
   */
  public String getSslUriStreaming() {

    return sslUriStreaming;
  }

  /**
   * Set the URI for SSL streaming.
   * 
   * @param sslUriStreaming the URI for ssl streaming
   */
  public void setSslUriStreaming(String sslUriStreaming) {

    this.sslUriStreaming = sslUriStreaming;
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
   * Get an item from the arbitrary exchange-specific parameters to be passed to the exchange implementation.
   * 
   * @return a Map of named exchange-specific parameter values
   */
  public Object getExchangeSpecificParametersItem(String key) {

    return exchangeSpecificParameters.get(key);
  }

  /**
   * Set an item in the arbitrary exchange-specific parameters to be passed to the exchange implementation.
   * 
   * @param exchangeSpecificParameters a Map of named exchange-specific parameter values
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

}
