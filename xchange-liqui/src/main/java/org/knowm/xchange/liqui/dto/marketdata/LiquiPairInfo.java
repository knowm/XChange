package org.knowm.xchange.liqui.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class LiquiPairInfo {

  private final int decimalPlaces;
  private final BigDecimal minPrice;
  private final BigDecimal maxPrice;
  private final BigDecimal minAmount;
  private final BigDecimal maxAmount;
  private final BigDecimal minTotal;
  private final boolean hidden;
  private final BigDecimal fee;

  public LiquiPairInfo(
      @JsonProperty("decimal_places") int decimalPlaces,
      @JsonProperty("min_price") BigDecimal minPrice,
      @JsonProperty("max_price") BigDecimal maxPrice,
      @JsonProperty("min_amount") BigDecimal minAmount,
      @JsonProperty("max_amount") BigDecimal maxAmount,
      @JsonProperty("min_total") BigDecimal minTotal,
      @JsonProperty("hidden") boolean hidden,
      @JsonProperty("fee") BigDecimal fee) {
    this.decimalPlaces = decimalPlaces;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.minAmount = minAmount;
    this.maxAmount = maxAmount;
    this.minTotal = minTotal;
    this.hidden = hidden;
    this.fee = fee;
  }

  public int getDecimalPlaces() {
    return this.decimalPlaces;
  }

  public BigDecimal getMinPrice() {
    return this.minPrice;
  }

  public BigDecimal getMaxPrice() {
    return this.maxPrice;
  }

  public BigDecimal getMinAmount() {
    return this.minAmount;
  }

  public BigDecimal getMaxAmount() {
    return this.maxAmount;
  }

  public BigDecimal getMinTotal() {
    return this.minTotal;
  }

  public boolean isHidden() {
    return this.hidden;
  }

  public BigDecimal getFee() {
    return this.fee;
  }

  @Override
  public String toString() {
    return "LiquiPairInfo{"
        + "decimalPlaces="
        + this.decimalPlaces
        + ", minPrice="
        + this.minPrice
        + ", maxPrice="
        + this.maxPrice
        + ", minAmount="
        + this.minAmount
        + ", maxAmount="
        + this.maxAmount
        + ", minTotal="
        + this.minTotal
        + ", hidden="
        + this.hidden
        + ", fee="
        + this.fee
        + '}';
  }
}
