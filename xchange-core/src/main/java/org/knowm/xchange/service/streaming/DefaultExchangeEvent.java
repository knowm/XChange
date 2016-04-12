package org.knowm.xchange.service.streaming;

/**
 * <p>
 * Exchange event that provides convenience constructors for JSON wrapping
 * </p>
 */
public class DefaultExchangeEvent implements ExchangeEvent {

  // Mandatory fields
  protected final ExchangeEventType exchangeEventType;
  protected final String data;

  // Optional fields
  protected Object payload = null;

  /**
   * @param exchangeEventType The exchange event type
   * @param data The raw message content (original reference is kept)
   */
  public DefaultExchangeEvent(ExchangeEventType exchangeEventType, String data) {

    this.exchangeEventType = exchangeEventType;
    this.data = data;
  }

  /**
   * @param exchangeEventType The exchange event type
   * @param data The raw message content (original reference is kept)
   * @param payload The processed message content (e.g. a Ticker)
   */
  public DefaultExchangeEvent(ExchangeEventType exchangeEventType, String data, Object payload) {

    this(exchangeEventType, data);
    this.payload = payload;
  }

  @Override
  public Object getPayload() {

    return payload;
  }

  @Override
  public String getData() {

    return data;

  }

  @Override
  public ExchangeEventType getEventType() {

    return exchangeEventType;
  }
}
