package org.knowm.xchange.dase.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DaseSingleBalance {

  private final BigDecimal total;
  private final BigDecimal available;
  private final BigDecimal blocked;

  @JsonCreator
  public DaseSingleBalance(
      @JsonProperty("total") BigDecimal total,
      @JsonProperty("available") BigDecimal available,
      @JsonProperty("blocked") BigDecimal blocked) {
    this.total = total;
    this.available = available;
    this.blocked = blocked;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getBlocked() {
    return blocked;
  }
}
