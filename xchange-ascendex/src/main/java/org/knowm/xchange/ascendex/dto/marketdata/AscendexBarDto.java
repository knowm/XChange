package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AscendexBarDto {
  private final Long timestamp;
  private final String interval;
  private final String openPrice;
  private final String closePrice;
  private final String highPrice;
  private final String lowPrice;
  private final String volume;

  public AscendexBarDto(
      @JsonProperty("ts") Long timestamp,
      @JsonProperty("i") String interval,
      @JsonProperty("o") String openPrice,
      @JsonProperty("c") String closePrice,
      @JsonProperty("h") String highPrice,
      @JsonProperty("l") String lowPrice,
      @JsonProperty("v") String volume) {
    this.timestamp = timestamp;
    this.interval = interval;
    this.openPrice = openPrice;
    this.closePrice = closePrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.volume = volume;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public String getInterval() {
    return interval;
  }

  public String getOpenPrice() {
    return openPrice;
  }

  public String getClosePrice() {
    return closePrice;
  }

  public String getHighPrice() {
    return highPrice;
  }

  public String getLowPrice() {
    return lowPrice;
  }

  public String getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "AscendexBarDto{"
        + "timestamp="
        + timestamp
        + ", interval='"
        + interval
        + '\''
        + ", openPrice='"
        + openPrice
        + '\''
        + ", closePrice="
        + closePrice
        + ", highPrice='"
        + highPrice
        + '\''
        + ", lowPrice='"
        + lowPrice
        + '\''
        + ", volume='"
        + volume
        + '\''
        + '}';
  }
}
