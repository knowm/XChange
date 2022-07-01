package org.knowm.xchange.lgo.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class LgoProductTotal {

  private final LgoLimit limits;

  public LgoProductTotal(@JsonProperty("limits") LgoLimit limits) {
    this.limits = limits;
  }

  public LgoLimit getLimits() {
    return limits;
  }
}
