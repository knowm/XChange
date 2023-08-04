package info.bitrich.xchangestream.binance;

import static java.util.concurrent.TimeUnit.SECONDS;

import info.bitrich.xchangestream.binance.exceptions.NoActiveChannelException;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceUserDataChannelImpl implements BinanceUserDataChannel {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceUserDataChannelImpl.class);

  private final BinanceAuthenticated binance;
  private final String apiKey;
  private final Runnable onApiCall;
  private final Disposable keepAlive;

  private String listenKey;
  private Consumer<String> onChangeListenKey;

  /**
   * Creates the channel, establishing a listen key (immediately available from {@link
   * #getListenKey()}) and starting timers to ensure the channel is kept alive.
   *
   * @param binance Access to binance services.
   * @param apiKey The API key.
   * @param onApiCall A callback to perform prior to any service calls.
   */
  protected BinanceUserDataChannelImpl(BinanceAuthenticated binance, String apiKey, Runnable onApiCall) {
    this.binance = binance;
    this.apiKey = apiKey;
    this.onApiCall = onApiCall;
    openChannel();
    // Send a keepalive every 30 minutes as recommended by Binance
    this.keepAlive = Observable.interval(30, TimeUnit.MINUTES).subscribe(x -> keepAlive());
  }

  @Override
  public void onChangeListenKey(Consumer<String> onChangeListenKey) {
    this.onChangeListenKey = onChangeListenKey;
  }

  private void keepAlive() {
    if (listenKey == null) return;
    try {
      LOG.debug("Keeping user data channel alive");
      onApiCall.run();
      binance.keepAliveUserDataStream(apiKey, listenKey);
      LOG.debug("User data channel keepalive sent successfully");
    } catch (Exception e) {
      LOG.error("User data channel keepalive failed.", e);
      this.listenKey = null;
      reconnect();
    }
  }

  private void reconnect() {
    try {
      openChannel();
      if (onChangeListenKey != null) {
        onChangeListenKey.accept(this.listenKey);
      }
    } catch (Exception e) {
      LOG.error("Failed to reconnect. Will retry in 15 seconds.", e);
      Observable.timer(15, SECONDS).subscribe(x -> reconnect());
    }
  }

  private void openChannel() {
    try {
      LOG.debug("Opening new user data channel");
      onApiCall.run();
      this.listenKey = binance.startUserDataStream(apiKey).getListenKey();
      LOG.debug("Opened new user data channel");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getListenKey() throws NoActiveChannelException {
    if (listenKey == null) throw new NoActiveChannelException();
    return listenKey;
  }

  @Override
  public void close() {
    keepAlive.dispose();
  }

}
