package org.knowm.xchange.cryptopia.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public final class CryptopiaMarketHistory {

  private final long tradePairId;
  private final String label;
  private final String type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal total;
  private final long timestamp;

  public CryptopiaMarketHistory(
      @JsonProperty("TradePairId") long tradePairId,
      @JsonProperty("Label") String label,
      @JsonProperty("Type") String type,
      @JsonProperty("Price") BigDecimal price,
      @JsonProperty("Amount") BigDecimal amount,
      @JsonProperty("Total") BigDecimal total,
      @JsonProperty("Timestamp") long timestamp) {
    this.tradePairId = tradePairId;
    this.label = label;
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.total = total;
    this.timestamp = timestamp;
  }

  public long getTradePairId() {
    return tradePairId;
  }

  public String getLabel() {
    return label;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "CryptopiaMarketHistory{"
        + "tradePairId="
        + tradePairId
        + ", label='"
        + label
        + '\''
        + ", type='"
        + type
        + '\''
        + ", price="
        + price
        + ", amount="
        + amount
        + ", total="
        + total
        + ", timestamp="
        + timestamp
        + '}';
  }
}
