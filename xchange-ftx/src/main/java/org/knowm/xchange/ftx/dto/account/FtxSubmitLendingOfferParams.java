package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxSubmitLendingOfferParams {

  @JsonProperty("coin")
  private final String coin;

  @JsonProperty("size")
  private final double size;

  @JsonProperty("rate")
  private final double rate;

  public FtxSubmitLendingOfferParams(
      @JsonProperty("coin") String coin,
      @JsonProperty("size") double size,
      @JsonProperty("rate") double rate) {
    this.coin = coin;
    this.size = size;
    this.rate = rate;
  }

  public String getCoin() {
    return coin;
  }

  public double getSize() {
    return size;
  }

  public double getRate() {
    return rate;
  }

  @Override
  public String toString() {
    return "FtxSubmitLendingOfferParams{"
        + "coin='"
        + coin
        + '\''
        + ", size="
        + size
        + ", rate="
        + rate
        + '}';
  }
}
