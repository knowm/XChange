package org.knowm.xchange.dase.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DaseBalanceItem {

  private final String id;
  private final String currency;
  private final BigDecimal total;
  private final BigDecimal available;
  private final BigDecimal blocked;

  @JsonCreator
  public DaseBalanceItem(
      @JsonProperty("id") String id,
      @JsonProperty("currency") String currency,
      @JsonProperty("total") BigDecimal total,
      @JsonProperty("available") BigDecimal available,
      @JsonProperty("blocked") BigDecimal blocked) {
    this.id = id;
    this.currency = currency;
    this.total = total;
    this.available = available;
    this.blocked = blocked;
  }

  public String getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
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
