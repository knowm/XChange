package org.knowm.xchange.dvchain.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DVChainLevel {
  private final BigDecimal sellPrice;
  private final BigDecimal buyPrice;
  private final BigDecimal maxQuantity;

  public DVChainLevel(
      @JsonProperty("sellPrice") BigDecimal sellPrice,
      @JsonProperty("buyPrice") BigDecimal buyPrice,
      @JsonProperty("maxQuantity") BigDecimal maxQuantity) {
    this.buyPrice = buyPrice;
    this.sellPrice = sellPrice;
    this.maxQuantity = maxQuantity;
  }

  public BigDecimal getBuyPrice() {
    return buyPrice;
  }

  public BigDecimal getMaxQuantity() {
    return maxQuantity;
  }

  public BigDecimal getSellPrice() {
    return sellPrice;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Level {sellPrice=");
    builder.append(sellPrice);
    builder.append(", buyPrice=");
    builder.append(buyPrice);
    builder.append(", maxQuantity=");
    builder.append(maxQuantity);
    builder.append("}");
    return builder.toString();
  }
}
