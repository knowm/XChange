package org.knowm.xchange.bitget.config;

import java.time.Clock;
import lombok.Data;

@Data
public final class Config {

  private Clock clock;

  private static Config instance = new Config();

  private Config() {
    clock = Clock.systemDefaultZone();
  }

  public static Config getInstance() {
    return instance;
  }
}
