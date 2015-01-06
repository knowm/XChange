package com.xeiam.xchange.bitcointoyou;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Felipe Micaroni Lalli
 */
public final class BitcoinToYouUtils {

  private static final AtomicLong incremental = new AtomicLong(System.currentTimeMillis());

  private BitcoinToYouUtils() {

  }

  public static Long getNonce() {

    return incremental.incrementAndGet();
  }
}
