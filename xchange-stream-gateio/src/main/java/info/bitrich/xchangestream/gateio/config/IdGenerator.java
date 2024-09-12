package info.bitrich.xchangestream.gateio.config;

import java.util.concurrent.ThreadLocalRandom;

public final class IdGenerator {

  private static IdGenerator instance = new IdGenerator();

  private IdGenerator() {
  }

  public static IdGenerator getInstance() {
    return instance;
  }


  public Long requestId() {
    return ThreadLocalRandom.current().nextLong();
  }
}


