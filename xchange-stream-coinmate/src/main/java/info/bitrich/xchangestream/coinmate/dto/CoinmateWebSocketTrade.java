package info.bitrich.xchangestream.coinmate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTransactionsEntry;

public class CoinmateWebSocketTrade {
  private final long timestamp;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String type;
  private final BigDecimal buyOrderId;
  private final BigDecimal sellOrderId;

  public CoinmateWebSocketTrade(
      @JsonProperty("type") String type,
      @JsonProperty("date") long timestamp,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("buyOrderId") BigDecimal buyOrderId,
      @JsonProperty("sellOrderId") BigDecimal sellOrderId) {
    this.timestamp = timestamp;
    this.price = price;
    this.amount = amount;
    this.type = type;
    this.buyOrderId = buyOrderId;
    this.sellOrderId = sellOrderId;
  }

  public CoinmateTransactionsEntry toTransactionEntry(String currencyPair) {
    return new CoinmateTransactionsEntry(timestamp, null, price, amount, currencyPair);
  }

  public long getTimestamp() {
    return timestamp;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getBuyOrderId() {
    return buyOrderId;
  }

  public BigDecimal getSellOrderId() {
    return sellOrderId;
  }
}
