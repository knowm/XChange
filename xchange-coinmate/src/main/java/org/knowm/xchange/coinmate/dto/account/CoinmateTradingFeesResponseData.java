package org.knowm.xchange.coinmate.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinmateTradingFeesResponseData {

  private final BigDecimal maker;

  private final BigDecimal taker;

  private final long timestamp;

  @JsonCreator
  public CoinmateTradingFeesResponseData(
      @JsonProperty("maker") BigDecimal maker,
      @JsonProperty("taker") BigDecimal taker,
      @JsonProperty("timestamp") long timestamp) {
    this.maker = maker;
    this.taker = taker;
    this.timestamp = timestamp;
  }

  public BigDecimal getMaker() {
    return maker;
  }

  public BigDecimal getTaker() {
    return taker;
  }

  public long getTimestamp() {
    return timestamp;
  }
}
