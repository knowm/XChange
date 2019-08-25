package org.knowm.xchange.lgo.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public final class LgoLimit {

  private final BigDecimal min;
  private final BigDecimal max;

  public LgoLimit(@JsonProperty("min") BigDecimal min, @JsonProperty("max") BigDecimal max) {
    this.min = min;
    this.max = max;
  }

  public BigDecimal getMin() {
    return min;
  }

  public BigDecimal getMax() {
    return max;
  }
}
