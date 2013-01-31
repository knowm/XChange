/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.service;

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
