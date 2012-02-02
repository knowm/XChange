package com.xeiam.xchange;

/**
 *  <p>Interface to provide the following to {@link SessionOptions}:</p>
 *  <ul>
 *  <li>Provision of type safety for exchange providers</li>
 *  </ul>
 *
 * @since 1.0.0
 *         
 */
public interface ExchangeProvider {
  /**
   * Start the session with the exchange
   *
   * @param sessionOptions The session options
   */
  void start(SessionOptions sessionOptions);

  /**
   * Stop the session with the exchange
   *
   * @param sessionOptions The session options
   */
  void stop(SessionOptions sessionOptions);
}
