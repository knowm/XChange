package org.knowm.xchange.globitex.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class GlobitexActiveOrder implements Serializable {

  @JsonProperty("orderId")
  private final String orderId;

  @JsonProperty("orderStatus")
  private final String orderStatus;

  @JsonProperty("lastTimestamp")
  private final long lastTimestamp;

  @JsonProperty("orderPrice")
  private final BigDecimal orderPrice;

  @JsonProperty("orderQuantity")
  private final BigDecimal orderQuantity;

  @JsonProperty("avgPrice")
  private final BigDecimal avgPrice;

  @JsonProperty("type")
  private final String type;

  @JsonProperty("timeInForce")
  private final String timeInForce;

  @JsonProperty("clientOrderId")
  private final String clientOrderId;

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("side")
  private final String side;

  @JsonProperty("account")
  private final String account;

  @JsonProperty("orderSource")
  private final String orderSource;

  @JsonProperty("leavesQuantity")
  private final BigDecimal leavesQuantity;

  @JsonProperty("cumQuantity")
  private final BigDecimal cumQuantity;

  @JsonProperty("execQuantity")
  private final BigDecimal execQuantity;

  @JsonProperty("stopPrice")
  @JsonIgnore
  private final BigDecimal stopPrice;

  @JsonProperty("expireTime")
  @JsonIgnore
  private final long expireTime;

  public GlobitexActiveOrder(
      @JsonProperty("orderId") String orderId,
      @JsonProperty("orderStatus") String orderStatus,
      @JsonProperty("lastTimestamp") long lastTimestamp,
      @JsonProperty("orderPrice") BigDecimal orderPrice,
      @JsonProperty("orderQuantity") BigDecimal orderQuantity,
      @JsonProperty("avgPrice") BigDecimal avgPrice,
      @JsonProperty("type") String type,
      @JsonProperty("timeInForce") String timeInForce,
      @JsonProperty("clientOrderId") String clientOrderId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") String side,
      @JsonProperty("account") String account,
      @JsonProperty("orderSource") String orderSource,
      @JsonProperty("leavesQuantity") BigDecimal leavesQuantity,
      @JsonProperty("cumQuantity") BigDecimal cumQuantity,
      @JsonProperty("execQuantity") BigDecimal execQuantity,
      @JsonProperty("stopPrice") BigDecimal stopPrice,
      @JsonProperty("expireTime") long expireTime) {
    super();
    this.orderId = orderId;
    this.orderStatus = orderStatus;
    this.lastTimestamp = lastTimestamp;
    this.orderPrice = orderPrice;
    this.orderQuantity = orderQuantity;
    this.avgPrice = avgPrice;
    this.type = type;
    this.timeInForce = timeInForce;
    this.clientOrderId = clientOrderId;
    this.symbol = symbol;
    this.side = side;
    this.account = account;
    this.orderSource = orderSource;
    this.leavesQuantity = leavesQuantity;
    this.cumQuantity = cumQuantity;
    this.execQuantity = execQuantity;
    this.stopPrice = stopPrice;
    this.expireTime = expireTime;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public long getLastTimestamp() {
    return lastTimestamp;
  }

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public BigDecimal getOrderQuantity() {
    return orderQuantity;
  }

  public BigDecimal getAvgPrice() {
    return avgPrice;
  }

  public String getType() {
    return type;
  }

  public String getTimeInForce() {
    return timeInForce;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getSide() {
    return side;
  }

  public String getAccount() {
    return account;
  }

  public String getOrderSource() {
    return orderSource;
  }

  public BigDecimal getLeavesQuantity() {
    return leavesQuantity;
  }

  public BigDecimal getCumQuantity() {
    return cumQuantity;
  }

  public BigDecimal getExecQuantity() {
    return execQuantity;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  public long getExpireTime() {
    return expireTime;
  }

  @Override
  public String toString() {
    return "GlobitexActiveOrder{"
        + "orderId='"
        + orderId
        + '\''
        + ", orderStatus='"
        + orderStatus
        + '\''
        + ", lastTimestamp="
        + lastTimestamp
        + ", orderPrice="
        + orderPrice
        + ", orderQuantity="
        + orderQuantity
        + ", avgPrice="
        + avgPrice
        + ", type='"
        + type
        + '\''
        + ", timeInForce='"
        + timeInForce
        + '\''
        + ", clientOrderId='"
        + clientOrderId
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", side='"
        + side
        + '\''
        + ", account='"
        + account
        + '\''
        + ", orderSource='"
        + orderSource
        + '\''
        + ", leavesQuantity="
        + leavesQuantity
        + ", cumQuantity="
        + cumQuantity
        + ", execQuantity="
        + execQuantity
        + ", stopPrice="
        + stopPrice
        + ", expireTime="
        + expireTime
        + '}';
  }
}
