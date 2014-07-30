package com.xeiam.xchange.cexio;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: brox
 * Since: 2/6/14
 */
public class CexIOUtils {

  private static final long START_MILLIS = 1388534400000L; // Jan 1st, 2014 in milliseconds from epoch
  // counter for the nonce
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  /**
   * private Constructor
   */
  private CexIOUtils() {

  }

  public static int nextNonce() {

    return lastNonce.incrementAndGet();
  }

}
