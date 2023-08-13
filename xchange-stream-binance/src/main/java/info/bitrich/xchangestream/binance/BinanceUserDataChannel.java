package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.binance.exceptions.NoActiveChannelException;
import java.util.function.Consumer;

/**
 * Binance user data streams must be established by first requesting a unique "listen key" via
 * authenticated REST API, which is then used to create an obscured WS URI (rather than
 * authenticating the web socket). This class handles the initial request for a listen key, but also
 * the 30-minute keepalive REST calls necessary to keep the socket open. It also allows for the
 * possibility that extended downtime might cause the listen key to expire without being able to
 * renew it. In this case, a new listen key is requested and a caller can be alerted via
 * asynchronous callback to re-establish the socket with the new listen key.
 *
 * @author Graham Crockford
 */
public interface BinanceUserDataChannel extends AutoCloseable {

  /**
   * Set this callback to get notified if the current listen key is revoked.
   *
   * @param onChangeListenKey The callback.
   */
  void onChangeListenKey(Consumer<String> onChangeListenKey);

  /**
   * @return The current listen key.
   * @throws NoActiveChannelException If no listen key is currently available.
   */
  String getListenKey() throws NoActiveChannelException;

  void close();
}
