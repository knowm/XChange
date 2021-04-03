package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxLendingInfoDto {

  @JsonProperty("coin")
  private final String coin;

  @JsonProperty("lendable")
  private final double lendable;

  @JsonProperty("locked")
  private final double locked;

  @JsonProperty("minRate")
  private final double minRate;

  @JsonProperty("offered")
  private final double offered;

  @JsonCreator
  public FtxLendingInfoDto(
      @JsonProperty("coin") String coin,
      @JsonProperty("lendable") double lendable,
      @JsonProperty("locked") double locked,
      @JsonProperty("minRate") double minRate,
      @JsonProperty("offered") double offered) {
    this.coin = coin;
    this.lendable = lendable;
    this.locked = locked;
    this.minRate = minRate;
    this.offered = offered;
  }

  public String getCoin() {
    return coin;
  }

  public double getLendable() {
    return lendable;
  }

  public double getLocked() {
    return locked;
  }

  public double getMinRate() {
    return minRate;
  }

  public double getOffered() {
    return offered;
  }

  @Override
  public String toString() {
    return "FtxLendingInfoDto{"
        + "coin='"
        + coin
        + '\''
        + ", lendable="
        + lendable
        + ", locked="
        + locked
        + ", minRate="
        + minRate
        + ", offered="
        + offered
        + '}';
  }
}
