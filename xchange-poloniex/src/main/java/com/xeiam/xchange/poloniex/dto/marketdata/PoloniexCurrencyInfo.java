package com.xeiam.xchange.poloniex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PoloniexCurrencyInfo {

  private final BigDecimal maxDailyWithdrawal;
  private final BigDecimal txFee;
  private final int minConf;
  private final boolean disabled;

  public PoloniexCurrencyInfo(@JsonProperty("maxDailyWithdrawal") BigDecimal maxDailyWithdrawal, @JsonProperty("txFee") BigDecimal txFee,
      @JsonProperty("minConf") int minConf, @JsonProperty("disabled") boolean disabled) {

    this.maxDailyWithdrawal = maxDailyWithdrawal;
    this.txFee = txFee;
    this.minConf = minConf;
    this.disabled = disabled;
  }

  public BigDecimal getMaxDailyWithdrawal() {

    return maxDailyWithdrawal;
  }

  public BigDecimal getTxFee() {

    return txFee;
  }

  public int getMinConf() {

    return minConf;
  }

  public boolean isDisabled() {

    return disabled;
  }

  @Override
  public String toString() {

    return "PoloniexCurrencyInfo [maxDailyWithdrawal=" + maxDailyWithdrawal + ", txFee=" + txFee + ", minConf=" + minConf + ", disabled=" + disabled
        + "]";
  }
}
