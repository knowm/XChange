package com.xeiam.xchange.lakebtc.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class LakeBTCTickers {

  private final LakeBTCTicker usd;
  private final LakeBTCTicker cny;

  /**
   * Constructor
   *
   * @param usd
   * @param cny
   */
  public LakeBTCTickers(@JsonProperty("USD") LakeBTCTicker usd, @JsonProperty("CNY") LakeBTCTicker cny) {
    this.usd = usd;
    this.cny = cny;
  }

  public LakeBTCTicker getUsd() {
    return usd;
  }

  public LakeBTCTicker getCny() {
    return cny;
  }

  @Override
  public String toString() {
    return "LakeBTCTickers{" +
        "usd=" + usd +
        ", cny=" + cny +
        '}';
  }
}
