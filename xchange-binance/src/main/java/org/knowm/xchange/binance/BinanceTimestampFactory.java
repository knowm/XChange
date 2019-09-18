package org.knowm.xchange.binance;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceTimestampFactory implements SynchronizedValueFactory<Long> {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceTimestampFactory.class);

  private final Binance binance;

  private Long deltaServerTimeExpire;
  private Long deltaServerTime;

  public BinanceTimestampFactory(Binance binance) {
    this.binance = binance;
  }

  @Override
  public Long createValue() {

    return System.currentTimeMillis();
  }

  public void clearDeltaServerTime() {
    deltaServerTime = null;
  }

  public long deltaServerTime() throws IOException {

    if (deltaServerTime == null || deltaServerTimeExpire <= System.currentTimeMillis()) {

      // Do a little warm up
      Date serverTime = new Date(binance.time().getServerTime().getTime());

      // Assume that we are closer to the server time when we get the repose
      Date systemTime = new Date(System.currentTimeMillis());

      // Expire every 10min
      deltaServerTimeExpire = systemTime.getTime() + TimeUnit.MINUTES.toMillis(10);
      deltaServerTime = serverTime.getTime() - systemTime.getTime();

      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
      LOG.trace(
          "deltaServerTime: {} - {} => {}",
          df.format(serverTime),
          df.format(systemTime),
          deltaServerTime);
    }

    return deltaServerTime;
  }
}
