package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class FtxBorrowingInfoDto {

  @JsonProperty("coin")
  private final String coin;

  @JsonProperty("lendable")
  private final BigDecimal lendable;

  @JsonProperty("locked")
  private final BigDecimal locked;

  @JsonProperty("minRate")
  private final BigDecimal minRate;

  @JsonProperty("offered")
  private final BigDecimal offered;

  @JsonCreator
  public FtxBorrowingInfoDto(
      @JsonProperty("coin") String coin,
      @JsonProperty("lendable") BigDecimal lendable,
      @JsonProperty("locked") BigDecimal locked,
      @JsonProperty("minRate") BigDecimal minRate,
      @JsonProperty("offered") BigDecimal offered) {
    this.coin = coin;
    this.lendable = lendable;
    this.locked = locked;
    this.minRate = minRate;
    this.offered = offered;
  }

  public String getCoin() {
    return coin;
  }

  public BigDecimal getLendable() {
    return lendable;
  }

  public BigDecimal getLocked() {
    return locked;
  }

  public BigDecimal getMinRate() {
    return minRate;
  }

  public BigDecimal getOffered() {
    return offered;
  }

  @Override
  public String toString() {
    return "FtxBorrowingInfoDto{"
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
