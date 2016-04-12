package org.knowm.xchange.service.streaming;

/**
 * <p>
 * Exchange event that provides convenience constructors for JSON wrapping
 * </p>
 */
public class JsonWrappedExchangeEvent extends DefaultExchangeEvent {

  /**
   * @param exchangeEventType The exchange event type
   * @param message The message content without JSON wrapping (will get a {"message":"parameter value"} wrapping)
   */
  public JsonWrappedExchangeEvent(ExchangeEventType exchangeEventType, String message) {

    super(exchangeEventType, ("{\"message\":\"" + message + "\"}"));
  }

}
