package org.knowm.xchange.bitrue.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;

public final class BitrueBalance {

  private final Currency currency;
  private final BigDecimal free;
  private final BigDecimal locked;

  public BitrueBalance(
      @JsonProperty("asset") String asset,
      @JsonProperty("free") BigDecimal free,
      @JsonProperty("locked") BigDecimal locked) {
    this.currency = Currency.getInstance(asset);
    this.locked = locked;
    this.free = free;
  }

  public Currency getCurrency() {
    return currency;
  }

  public BigDecimal getTotal() {
    return free.add(locked);
  }

  public BigDecimal getAvailable() {
    return free;
  }

  public BigDecimal getLocked() {
    return locked;
  }

  public String toString() {
    return "[" + currency + ", free=" + free + ", locked=" + locked + "]";
  }
}
