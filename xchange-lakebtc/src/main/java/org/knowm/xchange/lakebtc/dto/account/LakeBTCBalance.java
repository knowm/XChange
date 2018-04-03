package org.knowm.xchange.lakebtc.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** User: cristian.lucaci Date: 10/3/2014 Time: 4:54 PM */
public class LakeBTCBalance {
  private final BigDecimal USD;
  private final BigDecimal CNY;
  private final BigDecimal BTC;

  public LakeBTCBalance(
      @JsonProperty("USD") BigDecimal USD,
      @JsonProperty("CNY") BigDecimal CNY,
      @JsonProperty("BTC") BigDecimal BTC) {
    this.USD = USD;
    this.CNY = CNY;
    this.BTC = BTC;
  }

  public BigDecimal getUSD() {
    return USD;
  }

  public BigDecimal getCNY() {
    return CNY;
  }

  public BigDecimal getBTC() {
    return BTC;
  }
}
