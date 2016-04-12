package org.knowm.xchange.service.streaming;

import org.knowm.xchange.exceptions.ExchangeException;

/**
 * <p>
 * Abstract base class to provide the following to XChange clients:
 * </p>
 * <ul>
 * <li>Simple extension point for a {@link Runnable} designed for use with an ExecutorService</li>
 * </ul>
 */
public abstract class ExchangeEventListener {

  /**
   * Constructor
   */
  public ExchangeEventListener() {

  }

  /**
   * <p>
   * Client code is expected to implement this in a manner specific to their own application
   * </p>
   *
   * @param event The exchange event containing the information
   */
  public abstract void handleEvent(ExchangeEvent event) throws ExchangeException;
}
