package com.xeiam.xchange.bitcurex;

/**
 * A central place for shared Bitcurex properties
 */
public final class BitcurexUtils {

  /**
   * private Constructor
   */
  private BitcurexUtils() {

  }

  public static long getNonce() {

    return System.currentTimeMillis();
  }

}
