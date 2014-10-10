package com.xeiam.xchange.vaultofsatoshi;

/**
 * A central place for shared VaultOfSatoshi properties
 */
public final class VaultOfSatoshiUtils {

  /**
   * private Constructor
   */
  private VaultOfSatoshiUtils() {

  }

  // VoS uses microseconds since epoch as a nonce. Needs to be with 10 seconds of server time
  public static long getNonce() {

    return System.currentTimeMillis() * 1000;
  }

}
