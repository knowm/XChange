package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxBorrowingRatesDto {

  @JsonProperty("coin")
  private final String coin;

  @JsonProperty("previous")
  private final double previous;

  @JsonProperty("estimate")
  private final double estimate;

  @JsonCreator
  public FtxBorrowingRatesDto(
      @JsonProperty("coin") String coin,
      @JsonProperty("previous") double previous,
      @JsonProperty("estimate") double estimate) {
    this.coin = coin;
    this.previous = previous;
    this.estimate = estimate;
  }

  public String getCoin() {
    return coin;
  }

  public double getPrevious() {
    return previous;
  }

  public double getEstimate() {
    return estimate;
  }

  @Override
  public String toString() {
    return "FtxBorrowingRatesDto{"
        + "coin='"
        + coin
        + '\''
        + ", previous="
        + previous
        + ", estimate="
        + estimate
        + '}';
  }
}
