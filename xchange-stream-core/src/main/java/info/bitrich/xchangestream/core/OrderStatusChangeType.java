package info.bitrich.xchangestream.core;

/**
 * Highly generalised set of status change types for orders which can be
 * provided in an authenticated stream by exchanges. Note that this does not
 * cover all the granular options any particular exchange supports; it is
 * intended to be a lowest common denominator.
 *
 * <p>The information returned here can be extended in future, provided that
 * it can be shown that there is widespread support for that data in
 * supported exchanges.</p>
 *
 * @author Graham Crockford
 */
public enum OrderStatusChangeType {

  /**
   * The order was added to the order book on the exchange.
   */
  OPENED,

  /**
   * The order was removed from the order book on the exchange. It may have
   * been filled, cancelled, filled, expired or removed for any other reason.
   */
  CLOSED
}