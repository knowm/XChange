package org.knowm.xchange.service.streaming;

/**
 * <p>
 * Event interface to provide the following to API:
 * </p>
 * <ul>
 * <li>Provision of exchange information to listeners through different implementations</li>
 * </ul>
 */
public interface ExchangeEvent {

  /**
   * @return The processed data provided by the upstream server (can be null)
   */
  Object getPayload();

  /**
   * @return The raw data provided by the upstream server
   */
  String getData();

  /**
   * @return The ExchangeEventType
   */
  ExchangeEventType getEventType();

}
