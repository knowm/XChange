package org.knowm.xchange.bitmex.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Clock;
import lombok.Data;

@Data
public final class Config {

  private ObjectMapper objectMapper;
  private Clock clock;

  private static Config instance = new Config();

  private Config() {
    clock = Clock.systemDefaultZone();
  }

  public static Config getInstance() {
    return instance;
  }
}
