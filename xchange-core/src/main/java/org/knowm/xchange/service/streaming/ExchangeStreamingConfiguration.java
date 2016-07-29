package org.knowm.xchange.service.streaming;

/**
 * <p>
 * Signature interface to provide the following to exchange services:
 * </p>
 * <ul>
 * <li>A common entry point for the application of configuration data</li>
 * </ul>
 * <p>
 * Often it is necessary for additional configuration to be applied to the exchange data feeds after the initial creation with ExchangeSpecification
 * has completed. This is the mechanism to achieve this.
 * </p>
 */
public interface ExchangeStreamingConfiguration {

  /**
   * What are the maximum number of reconnect attempts?
   * 
   * @return
   */
  public int getMaxReconnectAttempts();

  /**
   * Before attempting reconnect, how much of a delay?
   * 
   * @return
   */
  public int getReconnectWaitTimeInMs();

  /**
   * How much time should elapse before the connection is considered dead and a reconnect attempt should be made?
   * 
   * @return
   */
  public int getTimeoutInMs();

  /**
   * should it use an encrypted channel or not? (ws vs. wss protocol)
   * 
   * @return
   */
  public boolean isEncryptedChannel();

  /**
   * should it keep the socket alive? (send ping every 15s)
   * 
   * @return
   */
  public boolean keepAlive();

}
