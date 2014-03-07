/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.coinfloor.dto.streaming;

import java.util.Map;

import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventType;

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
