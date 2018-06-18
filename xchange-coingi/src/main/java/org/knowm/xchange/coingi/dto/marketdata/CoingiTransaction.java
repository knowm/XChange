package org.knowm.xchange.coingi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class CoingiTransaction {

  private String id;

  private long timestamp;

  private Map<String, String> currencyPair;

  private BigDecimal amount;

  private BigDecimal price;

  private int type;

  public CoingiTransaction(
      @JsonProperty("id") String id,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("currencyPair") Map<String, String> currencyPair,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("type") int type) {
    this.id = id;
    this.timestamp = timestamp;
    this.currencyPair = Objects.requireNonNull(currencyPair);
    this.amount = Objects.requireNonNull(amount);
    this.price = Objects.requireNonNull(price);
    this.type = type;
  }

  CoingiTransaction() {}

  public String getId() {
    return id;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public Map<String, String> getCurrencyPair() {
    return currencyPair;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public int getType() {
    return type;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CoingiTransaction that = (CoingiTransaction) o;
    return timestamp == that.timestamp
        && type == that.type
        && Objects.equals(id, that.id)
        && Objects.equals(currencyPair, that.currencyPair)
        && Objects.equals(amount, that.amount)
        && Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, timestamp, currencyPair, amount, price, type);
  }
}
