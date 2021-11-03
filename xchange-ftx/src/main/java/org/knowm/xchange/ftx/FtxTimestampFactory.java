package org.knowm.xchange.ftx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class FtxTimestampFactory implements SynchronizedValueFactory<Long> {

  private static final Logger LOG = LoggerFactory.getLogger(FtxTimestampFactory.class);

  private final FtxOtc ftxOtc;

  private volatile long deltaServerTimeExpire;
  private volatile long deltaServerTime;

  public FtxTimestampFactory(FtxOtc ftxOtc) {
    this.ftxOtc = ftxOtc;
  }

  @Override
  public Long createValue() {
    return System.currentTimeMillis() + deltaServerTime();
  }

  public long deltaServerTime() {
    if (deltaServerTimeExpire <= System.currentTimeMillis()) {
      try {
        long serverTime = ftxTime();
        long systemTime = System.currentTimeMillis();

        // Expire every 10min
        deltaServerTimeExpire = systemTime + TimeUnit.MINUTES.toMillis(10);
        deltaServerTime = serverTime - systemTime;
      } catch (IOException e) {
        LOG.error("Unable to get server time", e);
        deltaServerTime = 0;
      }
    }
    return deltaServerTime;
  }

  private long ftxTime() throws IOException {
    String str = ftxOtc.time().getResult();
    return ZonedDateTime.parse(str, DateTimeFormatter.ISO_ZONED_DATE_TIME).toInstant().toEpochMilli();
  }
}
