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
 * 
 * @since 1.3.0  
 */
public class DefaultExchangeEvent implements ExchangeEvent {

  protected final byte[] internalRawData;
  protected final ExchangeEventType exchangeEventType;

  /**
   * @param exchangeEventType The exchange event type
   * @param rawData The raw message content (original reference is kept)
   */
  public DefaultExchangeEvent(ExchangeEventType exchangeEventType, byte[] rawData) {

    this.exchangeEventType = exchangeEventType;
    internalRawData = new byte[rawData.length];
    System.arraycopy(rawData, 0, internalRawData, 0, rawData.length);
  }

  @Override
  public byte[] getRawData() {

    // Avoid exposing the internal state to consumers
    byte[] rawDataClone = new byte[internalRawData.length];
    System.arraycopy(internalRawData, 0, rawDataClone, 0, internalRawData.length);

    return rawDataClone;

  }

  @Override
  public ExchangeEventType getEventType() {

    return exchangeEventType;
  }
}
