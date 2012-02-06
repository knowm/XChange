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
   * @return Any additional parameters that the {@link Exchange} may consume
   */
  public Map<String, Object> getParameters() {
    return parameters;
  }
}
