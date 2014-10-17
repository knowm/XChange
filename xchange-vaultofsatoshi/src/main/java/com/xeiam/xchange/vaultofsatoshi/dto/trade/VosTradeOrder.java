package com.xeiam.xchange.vaultofsatoshi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosCurrency;

/**
 * @author Michael Lagacé
 */

public final class VosTradeOrder {

  private int order_id;
  private String order_currency;
  private long order_date;
  private String payment_currency;
  private String type;
  private String status;
  private VosCurrency units;
  private VosCurrency units_remaining;
  private VosCurrency price;
  private VosCurrency fee;
  private VosCurrency total;
  private long date_completed;

  public VosTradeOrder(@JsonProperty("order_id") int order_id, @JsonProperty("order_currency") String order_currency, @JsonProperty("order_date") long order_date,
      @JsonProperty("payment_currency") String payment_currency, @JsonProperty("type") String type, @JsonProperty("status") String status, @JsonProperty("units") VosCurrency units,
      @JsonProperty("units_remaining") VosCurrency units_remaining, @JsonProperty("price") VosCurrency price, @JsonProperty("fee") VosCurrency fee, @JsonProperty("total") VosCurrency total,
      @JsonProperty("date_completed") long date_completed) {

    this.order_id = order_id;
    this.order_currency = order_currency;
    this.order_date = order_date;
    this.payment_currency = payment_currency;
    this.type = type;
    this.status = status;
    this.units = units;
    this.units_remaining = units_remaining;
    this.price = price;
    this.fee = fee;
    this.total = total;
    this.date_completed = date_completed;
  }

  public int getOrder_id() {

    return order_id;
  }

  public String getOrder_currency() {

    return order_currency;
  }

  public long getOrder_date() {

    return order_date;
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

  public VosCurrency getUnits() {

    return units;
  }

  public VosCurrency getUnits_remaining() {

    return units_remaining;
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

  public long getDate_completed() {

    return date_completed;
  }

  @Override
  public String toString() {

    return "VosOrder [order_id=" + order_id + ", order_currency=" + order_currency + ", order_date=" + order_date + ", payment_currency=" + payment_currency + ", type=" + type + ", status=" + status
        + ", units=" + units + ", units_remaining=" + units_remaining + ", price=" + price + ", fee=" + fee + ", total=" + total + ", date_completed=" + date_completed + "]";
  }

}
