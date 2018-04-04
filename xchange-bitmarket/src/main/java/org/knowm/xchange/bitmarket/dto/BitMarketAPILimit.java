package org.knowm.xchange.bitmarket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author kfonal */
public class BitMarketAPILimit {
  private final int used;
  private final int allowed;
  private final long expires;

  /**
   * Constructor
   *
   * @param used
   * @param allowed
   * @param expires
   */
  public BitMarketAPILimit(
      @JsonProperty("used") int used,
      @JsonProperty("allowed") int allowed,
      @JsonProperty("expires") long expires) {

    this.used = used;
    this.allowed = allowed;
    this.expires = expires;
  }

  public int getUsed() {

    return used;
  }

  public int getAllowed() {

    return allowed;
  }

  public long getExpires() {

    return expires;
  }
}
