package org.knowm.xchange;

import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to provide the following to {@link Exchange}:
 *
 * <ul>
 *   <li>Manages the creation of specific Exchange implementations using runtime dependencies
 * </ul>
 */
public enum ExchangeFactory {
  INSTANCE;

  // flags
  private final Logger log = LoggerFactory.getLogger(ExchangeFactory.class);

  /** Constructor */
  ExchangeFactory() {}

  /**
   * Create an Exchange object with default ExchangeSpecification
   *
   * <p>The factory is parameterized with the name of the exchange implementation class. This must
   * be a class extending {@link org.knowm.xchange.Exchange}.
   *
   * @param exchangeClassName the fully-qualified class name of the exchange
   * @return a new exchange instance configured with the default {@link
   *     org.knowm.xchange.ExchangeSpecification}
   */
  public Exchange createExchange(String exchangeClassName) {

    return createExchange(exchangeClassName, null, null);
  }

  /**
   * Create an Exchange object with default ExchangeSpecification
   *
   * <p>The factory is parameterized with the name of the exchange implementation class. This must
   * be a class extending {@link org.knowm.xchange.Exchange}.
   *
   * @param exchangeClass the class of the exchange
   * @return a new exchange instance configured with the default {@link
   *     org.knowm.xchange.ExchangeSpecification}
   */
  public <T extends Exchange> T createExchange(Class<T> exchangeClass) {

    return createExchange(exchangeClass, null, null);
  }

  /**
   * Create an Exchange object with default ExchangeSpecification with authentication info and API
   * keys provided through parameters
   *
   * <p>The factory is parameterized with the name of the exchange implementation class. This must
   * be a class extending {@link org.knowm.xchange.Exchange}.
   *
   * @param exchangeClassName the fully-qualified class name of the exchange
   * @param apiKey the public API key
   * @param secretKey the secret API key
   * @return a new exchange instance configured with the default {@link
   *     org.knowm.xchange.ExchangeSpecification}
   */
  public Exchange createExchange(String exchangeClassName, String apiKey, String secretKey) {

    Assert.notNull(exchangeClassName, "exchangeClassName cannot be null");

    log.debug("Creating default exchange from class name");

    Exchange exchange = createExchangeWithoutSpecification(exchangeClassName);

    ExchangeSpecification defaultExchangeSpecification = exchange.getDefaultExchangeSpecification();
    if (apiKey != null) defaultExchangeSpecification.setApiKey(apiKey);
    if (secretKey != null) defaultExchangeSpecification.setSecretKey(secretKey);
    exchange.applySpecification(defaultExchangeSpecification);

    return exchange;
  }

  /**
   * Create an Exchange object with default ExchangeSpecification with authentication info and API
   * keys provided through parameters
   *
   * <p>The factory is parameterized with the name of the exchange implementation class. This must
   * be a class extending {@link org.knowm.xchange.Exchange}.
   *
   * @param exchangeClass the class of the exchange
   * @param apiKey the public API key
   * @param secretKey the secret API key
   * @return a new exchange instance configured with the default {@link
   *     org.knowm.xchange.ExchangeSpecification}
   */
  public <T extends Exchange> T createExchange(
      Class<T> exchangeClass, String apiKey, String secretKey) {

    Assert.notNull(exchangeClass, "exchange cannot be null");

    log.debug("Creating default exchange from class name");

    T exchange = createExchangeWithoutSpecification(exchangeClass);

    ExchangeSpecification defaultExchangeSpecification = exchange.getDefaultExchangeSpecification();
    if (apiKey != null) defaultExchangeSpecification.setApiKey(apiKey);
    if (secretKey != null) defaultExchangeSpecification.setSecretKey(secretKey);
    exchange.applySpecification(defaultExchangeSpecification);

    return exchange;
  }

  /**
   * Create an Exchange object default ExchangeSpecification
   *
   * @param exchangeSpecification the exchange specification
   * @return a new exchange instance configured with the provided {@link
   *     org.knowm.xchange.ExchangeSpecification}
   */
  public Exchange createExchange(ExchangeSpecification exchangeSpecification) {

    Assert.notNull(exchangeSpecification, "exchangeSpecfication cannot be null");

    log.debug("Creating exchange from specification");

    String exchangeClassName = exchangeSpecification.getExchangeClassName();
    Exchange exchange = createExchangeWithoutSpecification(exchangeClassName);
    exchange.applySpecification(exchangeSpecification);
    return exchange;
  }

  /**
   * Create an Exchange object without default ExchangeSpecification
   *
   * <p>The factory is parameterized with the name of the exchange implementation class. This must
   * be a class extending {@link org.knowm.xchange.Exchange}.
   *
   * @param exchangeClassName the fully-qualified class name of the exchange
   * @return a new exchange instance configured with the default {@link
   *     org.knowm.xchange.ExchangeSpecification}
   */
  public Exchange createExchangeWithoutSpecification(String exchangeClassName) {

    Assert.notNull(exchangeClassName, "exchangeClassName cannot be null");

    log.debug("Creating default exchange from class name");
    // Attempt to create an instance of the exchange provider
    try {

      // Attempt to locate the exchange provider on the classpath

      Class exchangeProviderClass = Class.forName(exchangeClassName);

      // Test that the class implements Exchange
      if (Exchange.class.isAssignableFrom(exchangeProviderClass)) {
        // Instantiate through the default constructor and use the default exchange specification
        return createExchangeWithoutSpecification(exchangeProviderClass);
      } else {
        throw new ExchangeException(
            "Class '" + exchangeClassName + "' does not implement Exchange");
      }
    } catch (ClassNotFoundException e) {
      throw new ExchangeException("Problem creating Exchange (class not found)", e);
    }
  }

  /**
   * Create an Exchange object without default ExchangeSpecification
   *
   * <p>The factory is parameterized with the name of the exchange implementation class. This must
   * be a class extending {@link org.knowm.xchange.Exchange}.
   *
   * @param exchangeClass the class of the exchange
   * @return a new exchange instance configured with the default {@link
   *     org.knowm.xchange.ExchangeSpecification}
   */
  public <T extends Exchange> T createExchangeWithoutSpecification(Class<T> exchangeClass) {

    Assert.notNull(exchangeClass, "exchangeClassName cannot be null");

    log.debug("Creating default exchange from class name");

    // Attempt to create an instance of the exchange provider
    try {

      // Instantiate through the default constructor and use the default exchange specification
      return exchangeClass.newInstance();

    } catch (InstantiationException e) {
      throw new ExchangeException("Problem creating Exchange (instantiation)", e);
    } catch (IllegalAccessException e) {
      throw new ExchangeException("Problem creating Exchange (illegal access)", e);
    }
  }
}
