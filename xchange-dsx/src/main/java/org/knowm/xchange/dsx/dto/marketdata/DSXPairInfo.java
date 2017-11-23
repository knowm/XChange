package org.knowm.xchange.dsx.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public final class DSXPairInfo {

  private final int decimalsPrice;
  private final BigDecimal minPrice;
  private final BigDecimal maxPrice;
  private final BigDecimal minAmount;
  private final int hidden;
  private final BigDecimal fee;
  private final int decimalVolume;

  public DSXPairInfo(@JsonProperty("decimal_places") int decimalsPrice, @JsonProperty("min_price") BigDecimal minPrice,
      @JsonProperty("max_price") BigDecimal maxPrice, @JsonProperty("min_amount") BigDecimal minAmount,
      @JsonProperty("hidden") int hidden, @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("amount_decimal_places") int decimalVolume) {

    this.decimalsPrice = decimalsPrice;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.minAmount = minAmount;
    this.hidden = hidden;
    this.fee = fee;
    this.decimalVolume = decimalVolume;
  }

  public int getDecimalsPrice() {

    return decimalsPrice;
  }

  public BigDecimal getMinPrice() {

    return minPrice;
  }

  public BigDecimal getMaxPrice() {

    return maxPrice;
  }

  public int getHidden() {

    return hidden;
  }

  public BigDecimal getMinAmount() {

    return minAmount;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public int getDecimalVolume() {

    return decimalVolume;
  }

  @Override
  public String toString() {

    return "DSXPairInfo{" +
        "decimalsPrice=" + decimalsPrice +
        ", minPrice=" + minPrice +
        ", maxPrice=" + maxPrice +
        ", minAmount=" + minAmount +
        ", hidden=" + hidden +
        ", fee=" + fee +
        ", decimalVolume=" + decimalVolume +
        '}';
  }

}
