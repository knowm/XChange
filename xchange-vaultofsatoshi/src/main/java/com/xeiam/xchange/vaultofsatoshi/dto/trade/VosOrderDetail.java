package com.xeiam.xchange.vaultofsatoshi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosCurrency;

/**
 * @author Michael Lagacé
 */

public final class VosOrderDetail {

  private String order_currency;
  private long transaction_date;
  private String payment_currency;
  private String type;
  private String status;
  private VosCurrency units_traded;
  private VosCurrency price;
  private VosCurrency fee;
  private VosCurrency total;

  public VosOrderDetail(@JsonProperty("order_currency") String order_currency, @JsonProperty("transaction_date") long transaction_date, @JsonProperty("payment_currency") String payment_currency,
      @JsonProperty("type") String type, @JsonProperty("status") String status, @JsonProperty("units_traded") VosCurrency units_traded, @JsonProperty("price") VosCurrency price,
      @JsonProperty("fee") VosCurrency fee, @JsonProperty("total") VosCurrency total) {

    this.order_currency = order_currency;
    this.payment_currency = payment_currency;
    this.type = type;
    this.status = status;
    this.units_traded = units_traded;
    this.price = price;
    this.fee = fee;
    this.total = total;
    this.transaction_date = transaction_date;
  }

  public String getOrder_currency() {

    return order_currency;
  }

  public long getTransaction_date() {

    return transaction_date;
  }

  public String getPayment_currency() {

    return payment_currency;
  }

  public String getType() {

    return type;
  }

  public String getStatus() {

    return status;
  }

  public VosCurrency getUnits_traded() {

    return units_traded;
  }

  public VosCurrency getPrice() {

    return price;
  }

  public VosCurrency getFee() {

    return fee;
  }

  public VosCurrency getTotal() {

    return total;
  }

  @Override
  public String toString() {

    return "VosOrderDetail [order_currency=" + order_currency + ", transaction_date=" + transaction_date + ", payment_currency=" + payment_currency + ", type=" + type + ", status=" + status
        + ", units_traded=" + units_traded + ", price=" + price + ", fee=" + fee + ", total=" + total + "]";
  }

}
