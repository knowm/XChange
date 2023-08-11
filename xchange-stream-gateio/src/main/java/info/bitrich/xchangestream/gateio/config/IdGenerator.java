package info.bitrich.xchangestream.gateio.config;

import org.apache.commons.lang3.RandomUtils;

public final class IdGenerator {

  private static IdGenerator instance = new IdGenerator();

  private IdGenerator() {
  }

  public static IdGenerator getInstance() {
    return instance;
  }


  public Long requestId() {
    return RandomUtils.nextLong();
  }
}


