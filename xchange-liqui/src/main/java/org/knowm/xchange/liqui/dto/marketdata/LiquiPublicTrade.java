package org.knowm.xchange.liqui.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.liqui.dto.LiquiTradeType;

public class LiquiPublicTrade {

  private final LiquiTradeType type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final long tradeId;
  private final long timestamp;

  public LiquiPublicTrade(
      @JsonProperty("type") final LiquiTradeType type,
      @JsonProperty("price") final String price,
      @JsonProperty("amount") final String amount,
      @JsonProperty("tid") final long tradeId,
      @JsonProperty("timestamp") final long timestamp) {
    this.type = type;
    this.price = new BigDecimal(price);
    this.amount = new BigDecimal(amount);
    this.tradeId = tradeId;
    this.timestamp = timestamp;
  }

  public LiquiTradeType getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public long getTradeId() {
    return tradeId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "LiquiPublicTrade{"
        + "type="
        + type
        + ", price="
        + price
        + ", amount="
        + amount
        + ", tradeId="
        + tradeId
        + ", timestamp="
        + timestamp
        + '}';
  }
}
