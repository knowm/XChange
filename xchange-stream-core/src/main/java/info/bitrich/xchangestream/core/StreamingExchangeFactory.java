package info.bitrich.xchangestream.core;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to provide the following to {@link StreamingExchange}:
 *
 * <ul>
 *   <li>Manages the creation of specific Exchange implementations using runtime dependencies
 * </ul>
 */
public enum StreamingExchangeFactory {
  INSTANCE;

  // flags
  private final Logger LOG = LoggerFactory.getLogger(ExchangeFactory.class);

  /** Constructor */
  private StreamingExchangeFactory() {}

  /**
   * Create an Exchange object without default ExchangeSpecification
   *
   * <p>The factory is parameterised with the name of the exchange implementation class. This must
   * be a class extending {@link org.knowm.xchange.Exchange}.
   *
   * @param exchangeClassName the fully-qualified class name of the exchange
   * @return a new exchange instance configured with the default {@link
   *     org.knowm.xchange.ExchangeSpecification}
   */
  public StreamingExchange createExchangeWithoutSpecification(String exchangeClassName) {

    Assert.notNull(exchangeClassName, "exchangeClassName cannot be null");

    LOG.debug("Creating default exchange from class name");

    final Class<? extends StreamingExchange> exchangeClass =
        exchangeClassForName(exchangeClassName);
    return createExchangeWithoutSpecification(exchangeClass);
  }

  private static Class<? extends StreamingExchange> exchangeClassForName(String exchangeClassName) {
    // Attempt to create an instance of the exchange provider
    try {

      // Attempt to locate the exchange provider on the classpath
      Class<?> exchangeProviderClass = Class.forName(exchangeClassName);

      // Test that the class implements Exchange
      if (StreamingExchange.class.isAssignableFrom(exchangeProviderClass)) {
        return (Class<? extends StreamingExchange>) exchangeProviderClass;
      } else {
        throw new ExchangeException(
            "Class '" + exchangeClassName + "' does not implement Exchange");
      }
    } catch (ReflectiveOperationException e) {
      throw new ExchangeException("Problem creating Exchange ", e);
    }
  }

  public StreamingExchange createExchangeWithoutSpecification(
      Class<? extends StreamingExchange> exchangeClass) {
    Assert.notNull(exchangeClass, "exchangeClass cannot be null");
    LOG.debug("Creating default exchange from class name");

    // Attempt to create an instance of the exchange provider
    try {
      // Instantiate through the default constructor and use the default exchange specification
      return exchangeClass.getConstructor().newInstance();
    } catch (ReflectiveOperationException e) {
      throw new ExchangeException(
          "Problem creating Exchange for class: " + exchangeClass.getName(), e);
    }
  }

  /**
   * Create an Exchange object with default ExchangeSpecification
   *
   * <p>The factory is parameterised with the name of the exchange implementation class. This must
   * be a class extending {@link org.knowm.xchange.Exchange}.
   *
   * @param exchangeClassName the fully-qualified class name of the exchange
   * @return a new exchange instance configured with the default {@link
   *     org.knowm.xchange.ExchangeSpecification}
   * @see this#createExchange(Class) Use createExchange by class for better performance
   */
  public StreamingExchange createExchange(String exchangeClassName) {

    Assert.notNull(exchangeClassName, "exchangeClassName cannot be null");

    LOG.debug("Creating default exchange from class name");

    StreamingExchange exchange = createExchangeWithoutSpecification(exchangeClassName);
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    return exchange;
  }

  /**
   * Create an Exchange object with default ExchangeSpecification
   *
   * <p>The factory is parameterised with the name of the exchange implementation class. This must
   * be a class extending {@link org.knowm.xchange.Exchange}.
   *
   * @param exchangeClass the exchange to create
   * @return a new exchange instance configured with the default {@link
   *     org.knowm.xchange.ExchangeSpecification}
   */
  public StreamingExchange createExchange(Class<? extends StreamingExchange> exchangeClass) {

    Assert.notNull(exchangeClass, "exchangeClass cannot be null");

    LOG.debug("Creating default exchange from class name");

    StreamingExchange exchange = createExchangeWithoutSpecification(exchangeClass);
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    return exchange;
  }

  public StreamingExchange createExchange(ExchangeSpecification exchangeSpecification) {

    Assert.notNull(exchangeSpecification, "exchangeSpecfication cannot be null");

    LOG.debug("Creating exchange from specification");

    // Attempt to create an instance of the exchange provider
    Class<?> exchangeProviderClass = exchangeSpecification.getExchangeClass();
    try {
      // Test that the class implements Exchange
      if (Exchange.class.isAssignableFrom(exchangeProviderClass)) {
        // Instantiate through the default constructor
        StreamingExchange exchange =
            (StreamingExchange) exchangeProviderClass.getConstructor().newInstance();
        exchange.applySpecification(exchangeSpecification);
        return exchange;
      } else {
        throw new ExchangeException(
            "Class '" + exchangeProviderClass.getName() + "' does not implement Exchange");
      }
    } catch (ReflectiveOperationException e) {
      throw new ExchangeException("Problem starting exchange provider ", e);
    }

    // Cannot be here due to exceptions

  }
}
