package org.knowm.xchange.ascendex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class AscendexCashAccountBalanceDto {

  private final String asset;

  private final BigDecimal totalBalance;

  private final BigDecimal availableBalance;

  public AscendexCashAccountBalanceDto(
      @JsonProperty("asset") String asset,
      @JsonProperty("totalBalance") BigDecimal totalBalance,
      @JsonProperty("availableBalance") BigDecimal availableBalance) {
    this.asset = asset;
    this.totalBalance = totalBalance;
    this.availableBalance = availableBalance;
  }

  public String getAsset() {
    return asset;
  }

  public BigDecimal getTotalBalance() {
    return totalBalance;
  }

  public BigDecimal getAvailableBalance() {
    return availableBalance;
  }

  @Override
  public String toString() {
    return "AscendexCashAccountBalanceDto{"
        + "asset='"
        + asset
        + '\''
        + ", totalBalance="
        + totalBalance
        + ", availableBalance="
        + availableBalance
        + '}';
  }
}
