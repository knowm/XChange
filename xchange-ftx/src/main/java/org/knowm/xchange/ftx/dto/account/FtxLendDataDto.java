package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxLendDataDto {

  @JsonProperty("coin")
  private final String coin;

  @JsonProperty("locked")
  private final double locked;

  @JsonProperty("currentOffered")
  private final double currentOffered;

  @JsonProperty("newOffered")
  private final double newOffered;

  @JsonProperty("rate")
  private final double rate;

  public FtxLendDataDto(
      @JsonProperty("coin") String coin,
      @JsonProperty("locked") double locked,
      @JsonProperty("currentOffered") double currentOffered,
      @JsonProperty("newOffered") double newOffered,
      @JsonProperty("rate") double rate) {
    this.coin = coin;
    this.locked = locked;
    this.currentOffered = currentOffered;
    this.newOffered = newOffered;
    this.rate = rate;
  }

  public String getCoin() {
    return coin;
  }

  public double getLocked() {
    return locked;
  }

  public double getCurrentOffered() {
    return currentOffered;
  }

  public double getNewOffered() {
    return newOffered;
  }

  public double getRate() {
    return rate;
  }

  @Override
  public String toString() {
    return "FtxLendDataDto{"
        + "coin='"
        + coin
        + '\''
        + ", locked="
        + locked
        + ", currentOffered="
        + currentOffered
        + ", newOffered="
        + newOffered
        + ", rate="
        + rate
        + '}';
  }
}
