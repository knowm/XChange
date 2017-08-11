package org.knowm.xchange.cryptopia.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CryptopiaOrder {

  private final long tradePairId;
  private final String label;
  private final BigDecimal price;
  private final BigDecimal volume;
  private final BigDecimal total;

  public CryptopiaOrder(@JsonProperty("TradePairId") long tradePairId, @JsonProperty("Label") String label,
      @JsonProperty("Price") BigDecimal price, @JsonProperty("Volume") BigDecimal volume,
      @JsonProperty("Total") BigDecimal total) {
    this.tradePairId = tradePairId;
    this.label = label;
    this.price = price;
    this.volume = volume;
    this.total = total;
  }

  public long getTradePairId() {
    return tradePairId;
  }

  public String getLabel() {
    return label;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getTotal() {
    return total;
  }

  @Override
  public String toString() {
    return "Order{" +
        "tradePairId=" + tradePairId +
        ", label='" + label + '\'' +
        ", price=" + price +
        ", volume=" + volume +
        ", total=" + total +
        '}';
  }
}
