package org.knowm.xchange.bitfinex.config;

import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

@Data
public final class Config {

  private SynchronizedValueFactory<Long> nonceFactory;

  private static Config instance = new Config();

  private Config() {
    nonceFactory = new CurrentTimeIncrementalNonceFactory(TimeUnit.MILLISECONDS);
  }

  public static Config getInstance() {
    return instance;
  }
}
