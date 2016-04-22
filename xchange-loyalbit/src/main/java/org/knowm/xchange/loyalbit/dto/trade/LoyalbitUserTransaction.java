package org.knowm.xchange.loyalbit.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import si.mazi.rescu.ExceptionalReturnContentException;
import si.mazi.rescu.serialization.jackson.serializers.FloatingTimestampDeserializer;

/**
 * @author Matija Mazi
 */
public final class LoyalbitUserTransaction {

  @JsonProperty("id")
  Long id;
  @JsonProperty("order_id")
  Long orderId;
  @JsonProperty("microtime")
  @JsonDeserialize(using = FloatingTimestampDeserializer.class)
  Date microtime;
  @JsonProperty("type")
  LoyalbitOrder.Type type;
  @JsonProperty("amount")
  BigDecimal amount;
  @JsonProperty("price")
  BigDecimal price;
  @JsonProperty("subtotal")
  BigDecimal subtotal;
  @JsonProperty("fee")
  BigDecimal fee;
  @JsonProperty("feeUSD")
  BigDecimal feeUSD;
  @JsonProperty("total")
  BigDecimal total;

  public LoyalbitUserTransaction(@JsonProperty("id") Long id, @JsonProperty("order_id") Long orderId,
      @JsonProperty("microtime") @JsonDeserialize(using = FloatingTimestampDeserializer.class) Date microtime,
      @JsonProperty("type") LoyalbitOrder.Type type, @JsonProperty("amount") BigDecimal amount, @JsonProperty("price") BigDecimal price,
      @JsonProperty("subtotal") BigDecimal subtotal, @JsonProperty("fee") BigDecimal fee, @JsonProperty("feeUSD") BigDecimal feeUSD,
      @JsonProperty("total") BigDecimal total, @JsonProperty("status") Integer status) throws ExceptionalReturnContentException {
    if (Objects.equals(status, 0)) {
      throw new ExceptionalReturnContentException("Status indicates failure: " + status);
    }
    this.id = id;
    this.orderId = orderId;
    this.microtime = microtime;
    this.type = type;
    this.amount = amount;
    this.price = price;
    this.subtotal = subtotal;
    this.fee = fee;
    this.feeUSD = feeUSD;
    this.total = total;
  }

  public Long getId() {
    return id;
  }

  public Long getOrderId() {
    return orderId;
  }

  public Date getMicrotime() {
    return microtime;
  }

  public LoyalbitOrder.Type getType() {
    return type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSubtotal() {
    return subtotal;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getFeeUSD() {
    return feeUSD;
  }

  public BigDecimal getTotal() {
    return total;
  }

  @Override
  public String toString() {
    return "LoyalbitUserTransaction{" + "id=" + id + ", order_id=" + orderId + ", microtime=" + microtime + ", type=" + type + ", amount=" + amount
        + ", price=" + price + ", subtotal=" + subtotal + ", fee=" + fee + ", feeUSD=" + feeUSD + ", total=" + total + '}';
  }

}
