package org.knowm.xchange.coingi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Objects;

/** Depth range. */
public class CoingiDepthRange {
  private BigDecimal price;

  private BigDecimal amount;

  public CoingiDepthRange(
      @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount) {
    this.price = Objects.requireNonNull(price);
    this.amount = Objects.requireNonNull(amount);
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    CoingiDepthRange that = (CoingiDepthRange) o;
    return Objects.equals(price, that.price) && Objects.equals(amount, that.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price, amount);
  }
}
