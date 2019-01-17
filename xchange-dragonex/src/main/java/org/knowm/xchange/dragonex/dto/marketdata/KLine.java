package org.knowm.xchange.dragonex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class KLine {

  private final List<String> columns;
  private final List<KLineData> lists;

  public KLine(
      @JsonProperty("columns") List<String> columns, @JsonProperty("lists") List<KLineData> lists) {
    this.columns = columns;
    this.lists = lists;
  }

  public static class KLineData {
    private final String amount; // exchange amount
    private final String closePrice; // closing price
    private final String maxPrice; // highest price
    private final String minPrice; // lowest price
    private final String openPrice; // opening price
    private final String preClosePrice; // previous closing price
    private final long timestamp; // second-level timestamp
    private final String usdtAmount; // corresponding USDT exchange amount
    private final String volume; // exchange volume

    public KLineData(
        @JsonProperty("amount") String amount,
        @JsonProperty("close_price") String closePrice,
        @JsonProperty("max_price") String maxPrice,
        @JsonProperty("min_price") String minPrice,
        @JsonProperty("open_price") String openPrice,
        @JsonProperty("pre_close_price") String preClosePrice,
        @JsonProperty("timestamp") long timestamp,
        @JsonProperty("usdt_amount") String usdtAmount,
        @JsonProperty("volume") String volume) {

      this.amount = amount;
      this.closePrice = closePrice;
      this.maxPrice = maxPrice;
      this.minPrice = minPrice;
      this.openPrice = openPrice;
      this.preClosePrice = preClosePrice;
      this.timestamp = timestamp;
      this.usdtAmount = usdtAmount;
      this.volume = volume;
    }
  }
}
