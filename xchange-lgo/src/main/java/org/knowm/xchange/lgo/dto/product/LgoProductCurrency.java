package org.knowm.xchange.lgo.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public final class LgoProductCurrency {

  private final String id;
  private final BigDecimal increment;
  private final LgoLimit limits;

  public LgoProductCurrency(
      @JsonProperty("id") String id,
      @JsonProperty("increment") BigDecimal increment,
      @JsonProperty("limits") LgoLimit limits) {
    this.id = id;
    this.increment = increment;
    this.limits = limits;
  }

  public String getId() {
    return id;
  }

  public BigDecimal getIncrement() {
    return increment;
  }

  public LgoLimit getLimits() {
    return limits;
  }
}
