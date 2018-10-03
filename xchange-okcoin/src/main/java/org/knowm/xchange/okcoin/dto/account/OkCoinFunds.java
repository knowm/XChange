package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

public class OkCoinFunds {

  private final Map<String, BigDecimal> free;
  private final Map<String, BigDecimal> freezed;
  private final Map<String, BigDecimal> borrow;

  public OkCoinFunds(
      @JsonProperty("free") final Map<String, BigDecimal> free,
      @JsonProperty("freezed") final Map<String, BigDecimal> freezed,
      @JsonProperty(value = "borrow", required = false) final Map<String, BigDecimal> borrow) {

    this.free = free;
    this.freezed = freezed;
    this.borrow = borrow == null ? Collections.<String, BigDecimal>emptyMap() : borrow;
  }

  public Map<String, BigDecimal> getFree() {

    return free;
  }

  public Map<String, BigDecimal> getFreezed() {

    return freezed;
  }

  public Map<String, BigDecimal> getBorrow() {

    return borrow;
  }
}
