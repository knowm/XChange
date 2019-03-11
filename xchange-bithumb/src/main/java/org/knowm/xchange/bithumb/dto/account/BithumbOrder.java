package org.knowm.xchange.bithumb.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bithumb.BithumbAdapters;

public class BithumbOrder {

  private final long orderId;
  private final long orderDate;
  private final String orderCurrency;
  private final String paymentCurrency;
  private final BithumbAdapters.OrderType type;
  private final String status;
  private final BigDecimal units;
  private final BigDecimal unitsRemaining;
  private final BigDecimal price;
  private final BigDecimal fee;
  private final BigDecimal total;
  private final long dateCompleted;
  private final Map<String, Object> additionalProperties = new HashMap<>();

  public BithumbOrder(
      @JsonProperty("order_id") long orderId,
      @JsonProperty("order_date") long orderDate,
      @JsonProperty("order_currency") String orderCurrency,
      @JsonProperty("payment_currency") String paymentCurrency,
      @JsonProperty("type") BithumbAdapters.OrderType type,
      @JsonProperty("status") String status,
      @JsonProperty("units") BigDecimal units,
      @JsonProperty("units_remaining") BigDecimal unitsRemaining,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("total") BigDecimal total,
      @JsonProperty("date_completed") long dateCompleted) {
    this.orderId = orderId;
    this.orderCurrency = orderCurrency;
    this.orderDate = orderDate;
    this.paymentCurrency = paymentCurrency;
    this.type = type;
    this.status = status;
    this.units = units;
    this.unitsRemaining = unitsRemaining;
    this.price = price;
    this.fee = fee;
    this.total = total;
    this.dateCompleted = dateCompleted;
  }

  public long getOrderId() {
    return orderId;
  }

  public long getOrderDate() {
    return orderDate;
  }

  public String getOrderCurrency() {
    return orderCurrency;
  }

  public String getPaymentCurrency() {
    return paymentCurrency;
  }

  public BithumbAdapters.OrderType getType() {
    return type;
  }

  public String getStatus() {
    return status;
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

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public long getDateCompleted() {
    return dateCompleted;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {
    return "BithumbOrder{"
        + "orderId="
        + orderId
        + ", orderDate="
        + orderDate
        + ", orderCurrency='"
        + orderCurrency
        + '\''
        + ", paymentCurrency='"
        + paymentCurrency
        + '\''
        + ", type='"
        + type
        + '\''
        + ", status='"
        + status
        + '\''
        + ", units="
        + units
        + ", unitsRemaining="
        + unitsRemaining
        + ", price="
        + price
        + ", fee="
        + fee
        + ", total="
        + total
        + ", dateCompleted="
        + dateCompleted
        + '}';
  }
}
