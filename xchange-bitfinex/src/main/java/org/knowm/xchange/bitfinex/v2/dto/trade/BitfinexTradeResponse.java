package org.knowm.xchange.bitfinex.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexTradeResponse {

  private final long id;
  private final String pair;
  private final long mtsCreate;
  private final long orderId;
  private final BigDecimal execAmount;
  private final BigDecimal execPrice;
  private final String orderType;
  private final BigDecimal orderPrice;
  private final int maker; // 1 if true, -1 if false
  private final BigDecimal fee;
  private final String feeCurrency;

  @JsonCreator
  public BitfinexTradeResponse(
          @JsonProperty(value = "id") long id,
          @JsonProperty(value = "pair") String pair,
          @JsonProperty(value = "mtsCreate") Long mtsCreate,
          @JsonProperty(value = "orderId") long orderId,
          @JsonProperty(value = "execAmount") BigDecimal execAmount,
          @JsonProperty(value = "execPrice") BigDecimal execPrice,
          @JsonProperty(value = "orderType") String orderType,
          @JsonProperty(value = "orderPrice") BigDecimal orderPrice,
          @JsonProperty(value = "maker") Integer maker,
          @JsonProperty(value = "fee") BigDecimal fee,
          @JsonProperty(value = "feeCurrency") String feeCurrency) {
    this.id = id;
    this.pair = pair;
    this.mtsCreate = mtsCreate;
    this.orderId = orderId;
    this.execAmount = execAmount;
    this.execPrice = execPrice;
    this.orderType = orderType;
    this.orderPrice = orderPrice;
    this.maker = maker;
    this.fee = fee;
    this.feeCurrency = feeCurrency;
  }

  public long getId() {
    return id;
  }

  public String getPair() {
    return pair;
  }

  public long getMtsCreate() {
    return mtsCreate;
  }

  public long getOrderId() {
    return orderId;
  }

  public BigDecimal getExecAmount() {
    return execAmount;
  }

  public BigDecimal getExecPrice() {
    return execPrice;
  }

  public String getOrderType() {
    return orderType;
  }

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public Integer getMaker() {
    return maker;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getFeeCurrency() {
    return feeCurrency;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("BitfinexTradeResponse [");
    builder.append("id=");
    builder.append(id);
    builder.append(", pair=");
    builder.append(pair);
    builder.append(", mtsCreate=");
    builder.append(mtsCreate);
    builder.append(", orderId=");
    builder.append(orderId);
    builder.append(", execAmount=");
    builder.append(execAmount);
    builder.append(", execPrice=");
    builder.append(execPrice);
    builder.append(", orderType=");
    builder.append(orderType);
    builder.append(", orderPrice=");
    builder.append(orderPrice);
    builder.append(", maker=");
    builder.append(maker);
    builder.append(", fee=");
    builder.append(fee);
    builder.append(", feeCurrency='");
    builder.append(feeCurrency).append('\'');
    builder.append(']');
    return builder.toString();
  }

}
