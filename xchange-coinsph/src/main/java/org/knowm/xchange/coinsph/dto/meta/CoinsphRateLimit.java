package org.knowm.xchange.coinsph.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphRateLimit {

  private final String rateLimitType; // e.g., REQUEST_WEIGHT, ORDERS
  private final String interval; // e.g., MINUTE, SECOND, DAY
  private final int intervalNum;
  private final int limit;

  public CoinsphRateLimit(
      @JsonProperty("rateLimitType") String rateLimitType,
      @JsonProperty("interval") String interval,
      @JsonProperty("intervalNum") int intervalNum,
      @JsonProperty("limit") int limit) {
    this.rateLimitType = rateLimitType;
    this.interval = interval;
    this.intervalNum = intervalNum;
    this.limit = limit;
  }
}
