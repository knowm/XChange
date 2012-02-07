package com.xeiam.xchange;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Specification to provide the following to {@link ExchangeFactory}:</p>
 * <ul>
 * <li>Provision of required parameters for creating an {@link Exchange}</li>
 * <li>Provision of optional parameters for additional configuration</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class ExchangeSpecification {

  // Various public keys into the parameter map to aid consistency
  // Can be arbitrary so not an enum

  /**
   * The username for authentication
   */
  public static final String USERNAME = "username";
  /**
   * The password for authentication
   */
  public static final String PASSWORD = "password";
  /**
   * The API secret key typically used in HMAC signing of requests
   */
  public static final String API_SECRET_KEY = "api_secret_key";
  /**
   * The URI to reach the <b>root</b> of the exchange API<br/>
   * (e.g. use "https://example.com:8443/exchange", not "https://example.com:8443/exchange/api/v3/trades")
   */
  public static final String API_URI = "apiURI";
  /**
   * The numerical API version to use (e.g. "1" or "0.3" etc)
   */
  public static final String API_VERSION = "api_version";

  // Internal fields

  private final String exchangeClassName;

  private Map<String, Object> parameters = new HashMap<String, Object>();

  /**
   * Minimal constructor
   *
   * @param exchangeClassName The exchange class name (e.g. "com.xeiam.xchange.mtgox.v1.MtGoxExchange")
   */
  public ExchangeSpecification(String exchangeClassName) {
    this.exchangeClassName = exchangeClassName;
  }

  /**
   * Full constructor
   *
   * @param exchangeClassName The exchange class name (e.g. "com.xeiam.xchange.mtgox.v1.MtGoxExchange")
   * @param parameters        A map containing any additional parameters for the {@link Exchange} implementation
   */
  public ExchangeSpecification(String exchangeClassName, Map<String, Object> parameters) {
    this.exchangeClassName = exchangeClassName;
    this.parameters = parameters;
  }

  /**
   * @return The exchange class name for loading at runtime
   */
  public String getExchangeClassName() {
    return exchangeClassName;
  }

  /**
   * @param key The key into the parameter map (recommend using the provided standard static entries)
   * @return Any additional parameters that the {@link Exchange} may consume to configure services
   */
  public Object getParameter(String key) {
    return parameters.get(key);
  }
}
