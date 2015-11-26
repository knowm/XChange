package com.xeiam.xchange.poloniex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PoloniexCurrencyInfo {

  private final BigDecimal maxDailyWithdrawal;
  private final BigDecimal txFee;
  private final int minConf;
  private final boolean disabled;
  private final boolean frozen;
  private final boolean delisted;

  public PoloniexCurrencyInfo(@JsonProperty("maxDailyWithdrawal") BigDecimal maxDailyWithdrawal, @JsonProperty("txFee") BigDecimal txFee,
      @JsonProperty("minConf") int minConf, @JsonProperty("disabled") boolean disabled, @JsonProperty("frozen") boolean frozen,
      @JsonProperty("delisted") boolean delisted) {

    this.maxDailyWithdrawal = maxDailyWithdrawal;
    this.txFee = txFee;
    this.minConf = minConf;
    this.disabled = disabled;
    this.frozen = frozen;
    this.delisted = delisted;
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

  public boolean isFrozen() {

    return frozen;
  }

  public boolean isDelisted() {

    return delisted;
  }

  @Override
  public String toString() {

    return "PoloniexCurrencyInfo [maxDailyWithdrawal=" + maxDailyWithdrawal + ", txFee=" + txFee + ", minConf=" + minConf + ", disabled=" + disabled
        + ", frozen=" + frozen + ", delisted=" + delisted + "]";
  }
}
