package org.knowm.xchange.coingi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/** A single transaction */
public class CoingiUserTransaction {
  private String id;
  private long timestamp;
  private Map<String, String> currencyPair;
  private BigDecimal price;
  private BigDecimal baseAmount;
  private BigDecimal counterAmount;
  private float fee;
  private short type;
  private short orderType;
  private String orderId;

  public CoingiUserTransaction(
      @JsonProperty("id") String id,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("currencyPair") Map<String, String> currencyPair,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("baseAmount") BigDecimal baseAmount,
      @JsonProperty("counterAmount") BigDecimal counterAmount,
      @JsonProperty("fee") float fee,
      @JsonProperty("type") short type,
      @JsonProperty("orderType") short orderType,
      @JsonProperty("orderId") String orderId) {

    this.id = id;
    this.timestamp = timestamp;
    this.currencyPair = Objects.requireNonNull(currencyPair);
    this.price = Objects.requireNonNull(price);
    this.baseAmount = Objects.requireNonNull(baseAmount);
    this.counterAmount = Objects.requireNonNull(counterAmount);
    this.fee = fee;
    this.type = type;
    this.orderType = orderType;
    this.orderId = orderId;
  }

  CoingiUserTransaction() {}

  public String getId() {
    return id;
  }

  public Map<String, String> getCurrencyPair() {
    return currencyPair;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getBaseAmount() {
    return baseAmount;
  }

  public BigDecimal getCounterAmount() {
    return counterAmount;
  }

  public float getFee() {
    return fee;
  }

  public short getType() {
    return type;
  }

  public short getOrderType() {
    return orderType;
  }

  public String getOrderId() {
    return orderId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    CoingiUserTransaction that = (CoingiUserTransaction) o;
    return timestamp == that.timestamp
        && type == that.type
        && Objects.equals(id, that.id)
        && Objects.equals(price, that.price)
        && Objects.equals(currencyPair, that.currencyPair)
        && Objects.equals(baseAmount, that.baseAmount)
        && Objects.equals(counterAmount, that.counterAmount)
        && Objects.equals(fee, that.fee)
        && Objects.equals(orderType, that.orderType)
        && Objects.equals(orderId, that.orderId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, timestamp, currencyPair, price, baseAmount, counterAmount, fee, orderType, orderId);
  }
}
