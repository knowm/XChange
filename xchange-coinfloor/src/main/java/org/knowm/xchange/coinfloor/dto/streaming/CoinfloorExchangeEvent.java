package org.knowm.xchange.coinfloor.dto.streaming;

import java.util.Map;

import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;

/**
 * <p>
 * Exchange event that provides convenience constructors for JSON wrapping
 * </p>
 * 
 * @author obsessiveOrange
 */

public class CoinfloorExchangeEvent implements ExchangeEvent {

  // Mandatory fields
  protected final int tag;
  protected final ExchangeEventType exchangeEventType;
  protected final String data;

  // Optional fields (JSON data)
  protected Map<String, Object> payload = null;

  /**
   * @param exchangeEventType The exchange event type
   * @param data The raw message content (original reference is kept)
   */
  public CoinfloorExchangeEvent(int tag, ExchangeEventType exchangeEventType, String data) {

    this.tag = tag;
    this.exchangeEventType = exchangeEventType;
    this.data = data;
  }

  /**
   * @param exchangeEventType The exchange event type
   * @param data The raw message content (original reference is kept)
   * @param payload The processed message content (e.g. a Ticker)
   */
  public CoinfloorExchangeEvent(int tag, ExchangeEventType exchangeEventType, String data, Map<String, Object> payload) {

    this(tag, exchangeEventType, data);
    this.payload = payload;
  }

  public int getTag() {

    return tag;
  }

  @Override
  public String getData() {

    return data;
  }

  @Override
  public ExchangeEventType getEventType() {

    return exchangeEventType;
  }

  @Override
  public Map<String, Object> getPayload() {

    return payload;
  }

  public Object getPayloadGeneric() {

    return payload.get("Generic");
  }

  public Object getPayloadRaw() {

    return payload.get("Raw");
  }

  public Object getPayloadItem(String key) {

    return payload.get(key);
  }
}
