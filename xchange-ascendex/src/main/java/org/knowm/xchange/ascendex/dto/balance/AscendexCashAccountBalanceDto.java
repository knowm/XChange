package org.knowm.xchange.ascendex.dto.balance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexCashAccountBalanceDto {

  private String asset;

  private BigDecimal totalBalance;

  private BigDecimal availableBalance;
/*
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
  }*/
}
