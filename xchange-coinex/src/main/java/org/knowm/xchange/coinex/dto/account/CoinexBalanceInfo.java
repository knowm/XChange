package org.knowm.xchange.coinex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinexBalanceInfo {

  private final BigDecimal available;
  private final BigDecimal frozen;

  public CoinexBalanceInfo(
      @JsonProperty("available") BigDecimal available, @JsonProperty("frozen") BigDecimal frozen) {
    this.available = available;
    this.frozen = frozen;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getFrozen() {
    return frozen;
  }

  @Override
  public String toString() {
    return "CoinexBalanceInfo{" + "available=" + available + ", frozen=" + frozen + '}';
  }
}
