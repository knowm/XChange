package org.knowm.xchange.service.streaming;

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
   * A message with a market depth OrderBook payload
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
