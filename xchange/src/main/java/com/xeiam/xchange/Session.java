package com.xeiam.xchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  <p>Session to provide the following to consuming applications:</p>
 *  <ul>
 *  <li>Entry point to the exchange</li>
 *  </ul>
 *
 * @since 0.0.1
 *         
 */
public class Session {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(Session.class);
  
  private final SessionOptions sessionOptions;
  
  private ExchangeProvider exchangeProvider;

  public Session(SessionOptions sessionOptions) {
    this.sessionOptions = sessionOptions;
  }

  /**
   * <p>Starts the session using the underlying exchange provider</p>
   *
   * @throws SessionNotStartedException If something goes wrong
   */
  public void start() throws SessionNotStartedException {
    
    log.debug("Starting Session");
    
    // Attempt to create an instance of the exchange provider
    String exchangeProviderClassName = sessionOptions.getExchangeProviderClassName();
    try {
      
      // Attempt to locate the exchange provider on the classpath
      Class exchangeProviderClass = Class.forName(exchangeProviderClassName);
      
      // Test that the class implements ExchangeProvider
      if (ExchangeProvider.class.isAssignableFrom(exchangeProviderClass)) {
        // Instantiate through the default constructor
        exchangeProvider= (ExchangeProvider) exchangeProviderClass.newInstance(); 
      } else {
        throw new SessionNotStartedException("Class '"+exchangeProviderClassName+"' does not implement ExchangeProvider");
      }
    } catch (ClassNotFoundException e) {
      throw new SessionNotStartedException("Problem starting exchange provider (class not found)",e);
    } catch (InstantiationException e) {
      throw new SessionNotStartedException("Problem starting exchange provider (instantiation)",e);
    } catch (IllegalAccessException e) {
      throw new SessionNotStartedException("Problem starting exchange provider (illegal access)",e);
    }
    
    // Must be OK to be here
    log.debug("Instantiated '{}' OK. Starting provider...",exchangeProviderClassName);

    exchangeProvider.start(sessionOptions);

  }

  public void stop() {
    log.debug("Stopping Session");

    if (exchangeProvider != null) {
      exchangeProvider.stop(sessionOptions);
    }

    log.debug("Stopped OK");
  }


}
