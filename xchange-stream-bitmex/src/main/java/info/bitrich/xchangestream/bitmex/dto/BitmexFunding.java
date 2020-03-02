package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitmexFunding extends BitmexMarketDataEvent {

  private double fundingRate;

  private double fundingRateDaily;

  public BitmexFunding(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("fundingRate") double fundingRate,
      @JsonProperty("fundingRateDaily") double fundingRateDaily) {
    super(symbol, timestamp);
    this.fundingRate = fundingRate;
    this.fundingRateDaily = fundingRateDaily;
  }

  public double getFundingRate() {
    return fundingRate;
  }

  public double getFundingRateDaily() {
    return fundingRateDaily;
  }
}
