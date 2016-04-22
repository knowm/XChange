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
public class LoyalbitOrder {

  @JsonProperty("id")
  Long id;
  @JsonProperty("microtime")
  @JsonDeserialize(using = FloatingTimestampDeserializer.class)
  Date microtime;
  @JsonProperty("type")
  Type type;
  @JsonProperty("amount")
  BigDecimal amount;
  @JsonProperty("price")
  BigDecimal price;

  public LoyalbitOrder(@JsonProperty("id") Long id,
      @JsonProperty("microtime") @JsonDeserialize(using = FloatingTimestampDeserializer.class) Date microtime, @JsonProperty("type") Type type,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("price") BigDecimal price, @JsonProperty("status") Integer status)
      throws ExceptionalReturnContentException {
    if (Objects.equals(status, 0)) {
      throw new ExceptionalReturnContentException("Status indicates failure: " + status);
    }
    this.id = id;
    this.microtime = microtime;
    this.type = type;
    this.amount = amount;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public Date getMicrotime() {
    return microtime;
  }

  public Type getType() {
    return type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "LoyalbitOrder{" + "id=" + id + ", microtime=" + microtime + ", type=" + type + ", amount=" + amount + ", price=" + price + '}';
  }

  public enum Type {
    bid, ask
  }
}
