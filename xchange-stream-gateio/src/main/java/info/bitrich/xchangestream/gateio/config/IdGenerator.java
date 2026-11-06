package info.bitrich.xchangestream.gateio.config;

import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

public final class IdGenerator {

  @Getter
  private static IdGenerator instance = new IdGenerator();

  private IdGenerator() {}

  public Long requestIdRandom() {
    return ThreadLocalRandom.current().nextLong();
  }

  public String requestIdTimeMicroseconds() {
    return System.currentTimeMillis() + String.valueOf(System.nanoTime() / 1000 % 1000);
  }
}
