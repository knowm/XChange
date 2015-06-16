package com.xeiam.xchange.bitmarket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 24/04/15
 * Time: 12:33
 */
public class BitMarketRequestLimit {

  private Long used;
  private Long allowed;
  private Long expires;

  public BitMarketRequestLimit(@JsonProperty("used") Long used,
                               @JsonProperty("allowed") Long allowed,
                               @JsonProperty("expires") Long expires) {
    this.used = used;
    this.allowed = allowed;
    this.expires = expires;
  }

  public Long getUsed() {
    return used;
  }

  public Long getAllowed() {
    return allowed;
  }

  public Long getExpires() {
    return expires;
  }
}
