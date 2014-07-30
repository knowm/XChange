package com.xeiam.xchange.btce.v3.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: brox
 */

public class BTCEPairInfo {

  private final int decimals;
  private final BigDecimal minPrice;
  private final BigDecimal maxPrice;
  private final BigDecimal minAmount;
  private final int hidden;
  private final BigDecimal fee;

  public BTCEPairInfo(@JsonProperty("decimal_places") int decimals, @JsonProperty("min_price") BigDecimal minPrice, @JsonProperty("max_price") BigDecimal maxPrice,
      @JsonProperty("min_amount") BigDecimal minAmount, @JsonProperty("hidden") int hidden, @JsonProperty("fee") BigDecimal fee) {

    this.decimals = decimals;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.minAmount = minAmount;
    this.hidden = hidden;
    this.fee = fee;
  }

  public int getDecimals() {

    return decimals;
  }

  public BigDecimal getMinPrice() {

    return minPrice;
  }

  public BigDecimal getMaxPrice() {

    return maxPrice;
  }

  public BigDecimal getMinAmount() {

    return minAmount;
  }

  public int getHidden() {

    return hidden;
  }

  public BigDecimal getFee() {

    return fee;
  }

  @Override
  public String toString() {

    return "BTCEPairInfo [decimals=" + decimals + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", minAmount=" + minAmount + ", hidden=" + hidden + ", fee=" + fee + "]";
  }

}
