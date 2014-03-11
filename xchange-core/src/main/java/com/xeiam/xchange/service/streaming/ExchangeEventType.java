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
package com.xeiam.xchange.service.streaming;

/**
 * <p>
 * Enum to provide the following to exchange events:
 * </p>
 * <ul>
 * <li>Classification of event type to allow clients to take appropriate action</li>
 * </ul>
 */
public enum ExchangeEventType {

  // Connection messages

  /**
   * Issued when the upstream server has connected
   */
  CONNECT,

  /**
   * The upstream server has disconnected
   */
  DISCONNECT,

  /**
   * Represents an error condition
   */
  ERROR,

  // Generic message types
  /**
   * A message encoding some plain text
   */
  MESSAGE,

  /**
   * Represents an event (server specific info)
   */
  EVENT,

  // Specific message types to assist consumer processing

  /**
   * A message with welcome status
   */
  WELCOME,

  /**
   * A message with authentication status
   */
  AUTHENTICATION,

  /**
   * A message about subscription of market ticker
   */
  SUBSCRIBE_TICKER,

  /**
   * A message with a Ticker payload
   */
  TICKER,

  /**
   * A message with a Trade payload
   */
  TRADE,

  /**
   * A message about subscription of market depth
   */
  SUBSCRIBE_DEPTH,

  /**
   * A message with a Market Depth update payload
   */
  DEPTH,

  /**
   * A message of orderbook data
   */
  SUBSCRIBE_ORDERS,

  /**
   * A message containing the private id key
   */
  PRIVATE_ID_KEY,

  /**
   * A message with a user order
   */
  USER_ORDER,

  /**
   * A message sent when a user order is added
   */
  ORDER_ADDED,

  /**
   * A message sent when a user order is cancelled
   */
  ORDER_CANCELED,

  /**
   * A message with the trade lag
   */
  TRADE_LAG,

  /**
   * A message with user orders
   */
  USER_ORDERS_LIST,

  /**
   * A message with user accountInfo
   */
  ACCOUNT_INFO,

  /**
   * A message with user orders
   */
  USER_TRADE_VOLUME,

  /**
   * A message sent when a user order is added
   */
  USER_ORDER_ADDED,

  /**
   * A message sent when a user order is cancelled
   */
  USER_ORDER_CANCELED,

  /**
   * A message sent when a user order is not found
   */
  USER_ORDER_NOT_FOUND,

  /**
   * A message with wallet data
   */
  USER_WALLET,

  /**
   * A message with the wallet update
   */
  USER_WALLET_UPDATE,

  /**
   * A message with data about an estimated market order
   */
  USER_MARKET_ORDER_EST
}
