package org.knowm.xchange.bithumb.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bithumb.BithumbAdapters;

public class BithumbOrder {
  private final String orderCurrency;
  private final String paymentCurrency;
  private final String orderId;
  private final BithumbAdapters.OrderType type;
  private final Long orderDate;
  private final BigDecimal units;
  private final BigDecimal unitsRemaining;
  private final BigDecimal price;
  private final Map<String, Object> additionalProperties = new HashMap<>();

  public BithumbOrder(
      @JsonProperty("order_currency") String orderCurrency,
      @JsonProperty("payment_currency") String paymentCurrency,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("order_date") Long orderDate,
      @JsonProperty("type") BithumbAdapters.OrderType type,
      @JsonProperty("units") BigDecimal units,
      @JsonProperty("units_remaining") BigDecimal unitsRemaining,
      @JsonProperty("price") BigDecimal price) {
    this.orderCurrency = orderCurrency;
    this.paymentCurrency = paymentCurrency;
    this.orderId = orderId;
    this.orderDate = orderDate;
    this.type = type;
    this.units = units;
    this.unitsRemaining = unitsRemaining;
    this.price = price;
  }

  public String getOrderCurrency() {
    return orderCurrency;
  }

  public String getPaymentCurrency() {
    return paymentCurrency;
  }

  public String getOrderId() {
    return orderId;
  }

  public long getOrderDate() {
    return orderDate;
  }

  public BithumbAdapters.OrderType getType() {
    return type;
  }

  public BigDecimal getUnits() {
    return units;
  }

  public BigDecimal getUnitsRemaining() {
    return unitsRemaining;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {
    return "BithumbOrder{"
        + "orderCurrency='"
        + orderCurrency
        + '\''
        + ", paymentCurrency='"
        + paymentCurrency
        + '\''
        + ", orderId='"
        + orderId
        + '\''
        + ", type='"
        + type
        + '\''
        + ", orderDate="
        + orderDate
        + ", units="
        + units
        + ", unitsRemaining="
        + unitsRemaining
        + ", price="
        + price
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
