package org.knowm.xchange.mexc.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MEXCBalance {

  private final String frozen;
  private final String available;

  public MEXCBalance(
      @JsonProperty("frozen") String frozen, @JsonProperty("available") String available) {
    this.frozen = frozen;
    this.available = available;
  }

  public String getFrozen() {
    return frozen;
  }

  public String getAvailable() {
    return available;
  }
}
