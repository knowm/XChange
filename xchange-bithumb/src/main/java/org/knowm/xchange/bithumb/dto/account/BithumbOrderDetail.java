package org.knowm.xchange.bithumb.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BithumbOrderDetail {

  private final long transactionDate;
  private final String type;
  private final String orderCurrency;
  private final String paymentCurrency;
  private final BigDecimal unitsTraded;
  private final BigDecimal price;
  private final BigDecimal fee;
  private final BigDecimal total;

  public BithumbOrderDetail(
      @JsonProperty("transaction_date") long transactionDate,
      @JsonProperty("type") String type,
      @JsonProperty("order_currency") String orderCurrency,
      @JsonProperty("payment_currency") String paymentCurrency,
      @JsonProperty("units_traded") BigDecimal unitsTraded,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("total") BigDecimal total) {
    this.transactionDate = transactionDate;
    this.type = type;
    this.orderCurrency = orderCurrency;
    this.paymentCurrency = paymentCurrency;
    this.unitsTraded = unitsTraded;
    this.price = price;
    this.fee = fee;
    this.total = total;
  }

  public long getTransactionDate() {
    return transactionDate;
  }

  public String getType() {
    return type;
  }

  public String getOrderCurrency() {
    return orderCurrency;
  }

  public String getPaymentCurrency() {
    return paymentCurrency;
  }

  public BigDecimal getUnitsTraded() {
    return unitsTraded;
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

  @Override
  public String toString() {
    return "BithumbOrderDetail{"
        + "transactionDate="
        + transactionDate
        + ", type='"
        + type
        + '\''
        + ", orderCurrency='"
        + orderCurrency
        + '\''
        + ", paymentCurrency='"
        + paymentCurrency
        + '\''
        + ", unitsTraded="
        + unitsTraded
        + ", price="
        + price
        + ", fee="
        + fee
        + ", total="
        + total
        + '}';
  }
}
