package org.knowm.xchange.huobi.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcFuturesAccountInfo {

  private final BigDecimal usedMargin;
  private final BigDecimal availableMargin;
  private final BigDecimal frozenMargin;

  public BitVcFuturesAccountInfo(@JsonProperty("masterUsedMargin") final BigDecimal usedMargin,
      @JsonProperty("availableMargin") final BigDecimal availableMargin, @JsonProperty("frozenMargin") final BigDecimal frozenMargin) {

    this.usedMargin = usedMargin;
    this.availableMargin = availableMargin;
    this.frozenMargin = frozenMargin;
  }

  public BigDecimal getUsedMargin() {
    return usedMargin;
  }

  public BigDecimal getAvailableMargin() {
    return availableMargin;
  }

  public BigDecimal getFrozenMargin() {
    return frozenMargin;
  }
}
