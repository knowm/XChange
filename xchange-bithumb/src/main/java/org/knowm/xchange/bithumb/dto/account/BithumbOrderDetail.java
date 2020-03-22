package org.knowm.xchange.bithumb.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.bithumb.BithumbAdapters;

public class BithumbOrderDetail {
  private final Long orderDate;
  private final BithumbAdapters.OrderType type;
  private final String orderStatus;
  private final String orderCurrency;
  private final String paymentCurrency;
  private final BigDecimal orderPrice;
  private final BigDecimal orderQty;
  private final Long cancelDate;
  private final String cancelType;
  private final List<Contract> contract;
  private final Map<String, Object> additionalProperties = new HashMap<>();

  public BithumbOrderDetail(
      @JsonProperty("order_date") Long orderDate,
      @JsonProperty("type") BithumbAdapters.OrderType type,
      @JsonProperty("order_status") String orderStatus,
      @JsonProperty("order_currency") String orderCurrency,
      @JsonProperty("payment_currency") String paymentCurrency,
      @JsonProperty("order_price") BigDecimal orderPrice,
      @JsonProperty("order_qty") BigDecimal orderQty,
      @JsonProperty("cancel_date") Long cancelDate,
      @JsonProperty("cancel_type") String cancelType,
      @JsonProperty("contract") List<Contract> contract) {
    this.orderDate = orderDate;
    this.type = type;
    this.orderStatus = orderStatus;
    this.orderCurrency = orderCurrency;
    this.paymentCurrency = paymentCurrency;
    this.orderPrice = orderPrice;
    this.orderQty = orderQty;
    this.cancelDate = cancelDate;
    this.cancelType = cancelType;
    this.contract = contract;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  public Long getOrderDate() {
    return orderDate;
  }

  public BithumbAdapters.OrderType getType() {
    return type;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public String getOrderCurrency() {
    return orderCurrency;
  }

  public String getPaymentCurrency() {
    return paymentCurrency;
  }

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public BigDecimal getOrderQty() {
    return orderQty;
  }

  public Long getCancelDate() {
    return cancelDate;
  }

  public String getCancelType() {
    return cancelType;
  }

  public List<Contract> getContract() {
    return contract;
  }

  @Override
  public String toString() {
    return "BithumbOrderDetail{"
        + "orderDate="
        + orderDate
        + ", type="
        + type
        + ", orderStatus='"
        + orderStatus
        + '\''
        + ", orderCurrency='"
        + orderCurrency
        + '\''
        + ", paymentCurrency='"
        + paymentCurrency
        + '\''
        + ", orderPrice="
        + orderPrice
        + ", orderQty="
        + orderQty
        + ", cancelDate="
        + cancelDate
        + ", cancelType='"
        + cancelType
        + '\''
        + ", contract="
        + contract
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }

  public static class Contract {
    private final Long transactionDate;
    private final BigDecimal price;
    private final BigDecimal units;
    private final String feeCurrency;
    private final BigDecimal fee;
    private final BigDecimal total;

    public Contract(
        @JsonProperty("transaction_date") Long transactionDate,
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("units") BigDecimal units,
        @JsonProperty("fee_currency") String feeCurrency,
        @JsonProperty("fee") BigDecimal fee,
        @JsonProperty("total") BigDecimal total) {
      this.transactionDate = transactionDate;
      this.price = price;
      this.units = units;
      this.feeCurrency = feeCurrency;
      this.fee = fee;
      this.total = total;
    }

    public Long getTransactionDate() {
      return transactionDate;
    }

    public BigDecimal getPrice() {
      return price;
    }

    public BigDecimal getUnits() {
      return units;
    }

    public String getFeeCurrency() {
      return feeCurrency;
    }

    public BigDecimal getFee() {
      return fee;
    }

    public BigDecimal getTotal() {
      return total;
    }

    @Override
    public String toString() {
      return "Contract{"
          + "transactionDate="
          + transactionDate
          + ", price="
          + price
          + ", units="
          + units
          + ", feeCurrency='"
          + feeCurrency
          + '\''
          + ", fee="
          + fee
          + ", total="
          + total
          + '}';
    }
  }
}
