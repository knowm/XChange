package org.knowm.xchange.coindeal.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoindealBalance {

  @JsonProperty("currency")
  private final String currency;

  @JsonProperty("available")
  private final BigDecimal available;

  @JsonProperty("reserved")
  private final BigDecimal reserved;

  public CoindealBalance(
      @JsonProperty("currency") String currency,
      @JsonProperty("available") BigDecimal available,
      @JsonProperty("reserved") BigDecimal reserved) {
    this.currency = currency;
    this.available = available;
    this.reserved = reserved;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getReserved() {
    return reserved;
  }

  @Override
  public String toString() {
    return "CoindealBalance{"
        + "currency='"
        + currency
        + '\''
        + ", available='"
        + available
        + '\''
        + ", reserved='"
        + reserved
        + '\''
        + '}';
  }
}
