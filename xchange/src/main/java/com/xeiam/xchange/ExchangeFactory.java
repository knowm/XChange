package com.xeiam.xchange;

import com.xeiam.xchange.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Factory to provide the following to {@link Exchange}:</p>
 * <ul>
 * <li>Manages the creation of specific Exchange implementations using runtime dependencies</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public enum ExchangeFactory {

  INSTANCE;

  private static final Logger log = LoggerFactory.getLogger(ExchangeFactory.class);

  private ExchangeFactory() {
  }

  public Exchange createExchange(String exchangeClassName) {

    Assert.notNull(exchangeClassName, "exchangeClassName cannot be null");

    log.debug("Creating default exchange from class name");

    // Attempt to create an instance of the exchange provider
    try {

      // Attempt to locate the exchange provider on the classpath
      Class exchangeProviderClass = Class.forName(exchangeClassName);

      // Test that the class implements Exchange
      if (Exchange.class.isAssignableFrom(exchangeProviderClass)) {
        // Instantiate through the default constructor and use the default exchange specification
        Exchange exchange= (Exchange) exchangeProviderClass.newInstance();
        exchange.applySpecification(exchange.getDefaultExchangeSpecification());
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
        Exchange exchange= (Exchange) exchangeProviderClass.newInstance();
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
