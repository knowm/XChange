package org.knowm.xchange.okex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkexCandles {
  @JsonProperty("ts")
  private String ts; // Opening time of the candlestick, Unix timestamp format in milliseconds
  @JsonProperty("o")
  private String openPrice;
  @JsonProperty("h")
  private String highestPrice;
  @JsonProperty("l")
  private String lowestPrice;
  @JsonProperty("c")
  private String closePrice;
  @JsonProperty("vol")
  private String volume; //Trading volume, in szCcy
  @JsonProperty("confirm")
  private String confirm; //The state of candlesticks.0 represents that it is uncompleted, 1 represents that it is completed.
}
