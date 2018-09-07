package org.knowm.xchange.cobinhood.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CobinhoodCurrency {
  private final String symbol;
  private final String name;
  private final BigDecimal minUnit;
  private final BigDecimal depositFee;
  private final BigDecimal withdrawalFee;

  public CobinhoodCurrency(
      @JsonProperty("currency") String symbol,
      @JsonProperty("name") String name,
      @JsonProperty("min_unit") BigDecimal minUnit,
      @JsonProperty("deposit_fee") BigDecimal depositFee,
      @JsonProperty("withdrawal_fee") BigDecimal withdrawalFee) {
    this.symbol = symbol;
    this.name = name;
    this.minUnit = minUnit;
    this.depositFee = depositFee;
    this.withdrawalFee = withdrawalFee;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getMinUnit() {
    return minUnit;
  }

  public BigDecimal getDepositFee() {
    return depositFee;
  }

  public BigDecimal getWithdrawalFee() {
    return withdrawalFee;
  }
}
