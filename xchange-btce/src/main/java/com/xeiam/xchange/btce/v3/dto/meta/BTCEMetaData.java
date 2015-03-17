package com.xeiam.xchange.btce.v3.dto.meta;

public class BTCEMetaData {

  /**
   * The number of seconds the public data is cached for.
   */
  public final int publicInfoCacheSeconds;

  public final int amountScale;

  public BTCEMetaData(int publicInfoCacheSeconds, int amountScale) {
    this.publicInfoCacheSeconds = publicInfoCacheSeconds;
    this.amountScale = amountScale;
  }
}
