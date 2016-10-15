package org.knowm.xchange;

import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Factory to provide the following to {@link Exchange}:
 * </p>
 * <ul>
 * <li>Manages the creation of specific Exchange implementations using runtime dependencies</li>
 * </ul>
 */
public enum ExchangeFactory {

  INSTANCE;

  // flags
  private final Logger log = LoggerFactory.getLogger(ExchangeFactory.class);

  /**
   * Constructor
   */
  private ExchangeFactory() {

  }

  /**
   * Create an Exchange object without default ExchangeSpecification
   * <p>
   * The factory is parameterised with the name of the exchange implementation class. This must be a class extending
   * {@link org.knowm.xchange.Exchange}.
   * </p>
   * 
   * @param exchangeClassName the fully-qualified class name of the exchange
   * @return a new exchange instance configured with the default {@link org.knowm.xchange.ExchangeSpecification}
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
        Exchange exchange = (Exchange) exchangeProviderClass.newInstance();
        return exchange;
      } else {
        throw new ExchangeException("Class '" + exchangeClassName + "' does not implement Exchange");
      }
    } catch (ClassNotFoundException e) {
      throw new ExchangeException("Problem creating Exchange (class not found)", e);
    } catch (InstantiationException e) {
      throw new ExchangeException("Problem creating Exchange (instantiation)", e);
    } catch (IllegalAccessException e) {
      throw new ExchangeException("Problem creating Exchange (illegal access)", e);
    }

    // Cannot be here due to exceptions

  }

  /**
   * Create an Exchange object with default ExchangeSpecification
   * <p>
   * The factory is parameterised with the name of the exchange implementation class. This must be a class extending
   * {@link org.knowm.xchange.Exchange}.
   * </p>
   * 
   * @param exchangeClassName the fully-qualified class name of the exchange
   * @return a new exchange instance configured with the default {@link org.knowm.xchange.ExchangeSpecification}
   */
  public Exchange createExchange(String exchangeClassName) {

    Assert.notNull(exchangeClassName, "exchangeClassName cannot be null");

    log.debug("Creating default exchange from class name");

    Exchange exchange = createExchangeWithoutSpecification(exchangeClassName);
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    return exchange;

  }

  public Exchange createExchange(ExchangeSpecification exchangeSpecification) {

    Assert.notNull(exchangeSpecification, "exchangeSpecfication cannot be null");

    log.debug("Creating exchange from specification");

    String exchangeClassName = exchangeSpecification.getExchangeClassName();

    // Attempt to create an instance of the exchange provider
    try {

      // Attempt to locate the exchange provider on the classpath
      Class exchangeProviderClass = Class.forName(exchangeClassName);

      // Test that the class implements Exchange
      if (Exchange.class.isAssignableFrom(exchangeProviderClass)) {
        // Instantiate through the default constructor
        Exchange exchange = (Exchange) exchangeProviderClass.newInstance();
        exchange.applySpecification(exchangeSpecification);
        return exchange;
      } else {
        throw new ExchangeException("Class '" + exchangeClassName + "' does not implement Exchange");
      }
    } catch (ClassNotFoundException e) {
      throw new ExchangeException("Problem starting exchange provider (class not found)", e);
    } catch (InstantiationException e) {
      throw new ExchangeException("Problem starting exchange provider (instantiation)", e);
    } catch (IllegalAccessException e) {
      throw new ExchangeException("Problem starting exchange provider (illegal access)", e);
    }

    // Cannot be here due to exceptions

  }

}
