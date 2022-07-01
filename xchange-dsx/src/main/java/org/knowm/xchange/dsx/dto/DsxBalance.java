package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Objects;

public class DsxBalance {

  private final String currency;
  private final BigDecimal available;
  private final BigDecimal reserved;

  public DsxBalance(
      @JsonProperty("currency") String currency,
      @JsonProperty("available") BigDecimal available,
      @JsonProperty("reserved") BigDecimal reserved) {

    this.currency = currency;
    this.available = available;
    this.reserved = reserved;
  }

  @Override
  public String toString() {
    return "DsxBalance{"
        + "currency='"
        + currency
        + '\''
        + ", available="
        + available
        + ", reserved="
        + reserved
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DsxBalance)) return false;
    DsxBalance that = (DsxBalance) o;
    return Objects.equals(this.getCurrency(), that.getCurrency())
        && Objects.equals(this.getAvailable(), that.getAvailable())
        && Objects.equals(this.getReserved(), that.getReserved());
  }

  @Override
  public int hashCode() {

    return Objects.hash(this.getCurrency(), this.getAvailable(), this.getReserved());
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
}
