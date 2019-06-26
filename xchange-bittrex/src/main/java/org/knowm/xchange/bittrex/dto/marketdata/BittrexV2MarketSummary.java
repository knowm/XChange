package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexV2MarketSummary {
  private BittrexV2Market market;
  private BittrexV2Summary summary;
  private Boolean isVerified;

  public BittrexV2MarketSummary(
      @JsonProperty("Market") BittrexV2Market market,
      @JsonProperty("Summary") BittrexV2Summary summary,
      @JsonProperty("IsVerified") Boolean isVerified) {
    this.market = market;
    this.summary = summary;
    this.isVerified = isVerified;
  }

  public BittrexV2Market getMarket() {
    return market;
  }

  public void setMarket(BittrexV2Market market) {
    this.market = market;
  }

  public BittrexV2Summary getSummary() {
    return summary;
  }

  public void setSummary(BittrexV2Summary summary) {
    this.summary = summary;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public void setVerified(boolean verified) {
    isVerified = verified;
  }

  @Override
  public String toString() {
    return "BittrexV2MarketSummary{"
        + "market="
        + market
        + ", summary="
        + summary
        + ", isVerified="
        + isVerified
        + '}';
  }
}
